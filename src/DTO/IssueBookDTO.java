/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Manh Hai
 */
public class IssueBookDTO {
    private int book_id;
    private int member_id;
    private String status; // issued - returned - lost
    private String issue_date;
    private String return_date;
    private String note;
    
    BooksDTO book  = new BooksDTO();
    DTO.Func_Class func = new Func_Class();

    public IssueBookDTO() {
    }

    public IssueBookDTO(int book_id, int member_id, String status, String issue_date, String return_date, String note) {
        this.book_id = book_id;
        this.member_id = member_id;
        this.status = status;
        this.issue_date = issue_date;
        this.return_date = return_date;
        this.note = note;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    public void addIssue(int book_id, int member_id, String status, String issue_date, String return_date, String note) {
        
        String insertQuery = "INSERT INTO `issue_book`(`book_id`, `member_id`, `status`, `issue_date`, `return_date`, `note`) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(insertQuery);
            ps.setInt(1, book_id);
            ps.setInt(2, member_id);
            ps.setString(3, status);
            ps.setString(4, issue_date);
            ps.setString(5, return_date);
            ps.setString(6, note);

            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Issue added", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Issue not added", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IssueBookDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateIssue(int book_id, int member_id, String status, String issue_date, String return_date, String note) {
        
        String updateQuery = "UPDATE `issue_book` SET `status`=?, `return_date`=?,`note`=? WHERE `book_id`=? AND `member_id`=? AND `issue_date`=?";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(updateQuery);
           
            ps.setString(1, status);
            
            ps.setString(2, return_date);
            ps.setString(3, note);
            
            ps.setInt(4, book_id);
            ps.setInt(5, member_id);
            
            ps.setString(6, issue_date);

            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Status updated", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Status can not update", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IssueBookDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // check if this book is availabel
    // get the book quantity form table books and compare it to 
    // the number of book issued in the table issue_book
    public boolean checkBookAvailability(int book_id) {
        
        boolean availability = false;
        // first get the book quantity
        BooksDTO selected_book;
        try {
            selected_book = book.getBookByID(book_id);
            int quantity = selected_book.getQuantity();
            
            // get a number of books issued (the same book)
            int issued_book_count = countData(book_id);
            
            if(quantity > issued_book_count) {
                availability = true;
            } else {
                availability = false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(IssueBookDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return availability;
    }
    
    public int countData(int book_id) {
        int total = 0;
        ResultSet rs;
        PreparedStatement ps;
        
        try {
            ps = DB.getConnection().prepareStatement("SELECT COUNT(*) AS total FROM `issue_book` WHERE `book_id` = ? and `status` = 'issued'");
            ps.setInt(1, book_id);
            rs = ps.executeQuery();
            if(rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(IssueBookDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return total;
    }
    
    
    // function to populate an arrayList with issued/return/lost books
    public ArrayList<IssueBookDTO> issuedBookList(String statusBook) {
        
        ArrayList<IssueBookDTO> issuedBookList = new ArrayList<>();
        String query = "";
        
        if(statusBook.equals("")) {
            query = "SELECT * FROM `issue_book`";
        }
        else {
            query = "SELECT * FROM `issue_book` WHERE `status` = '" + statusBook + "'";
        }
        
        try {
            
            ResultSet rs = func.getData(query);
            
            IssueBookDTO issue_Book;
            
            while(rs.next()) {
                int book_id = rs.getInt(1);
                int member_id = rs.getInt(2);
                String status = rs.getString(3);
                String issue_date = rs.getString(4);
                String return_date = rs.getString(5);
                String note = rs.getString(6);
                
                issue_Book = new IssueBookDTO(book_id, member_id, status, issue_date, return_date, note);
                issuedBookList.add(issue_Book);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IssueBookDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return issuedBookList;
    }
    
    // remove a issued book by book_id, member_id and issued date
    public void removeFromIssuedTable(int book_id, int member_id, String issued_date) {
        
        String removeQuery = "DELETE FROM `issue_book` WHERE `book_id`=? AND `member_id`=? AND `issue_date`=?";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(removeQuery);

            ps.setInt(1, book_id);
            ps.setInt(2, member_id);
            ps.setString(3, issued_date);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Delete Successfully", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Not Deleted", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IssueBookDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
