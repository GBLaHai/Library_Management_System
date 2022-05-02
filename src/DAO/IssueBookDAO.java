/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.BookDTO;
import DTO.IssueBookDTO;
import My_Functions.Func_Class;
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
public class IssueBookDAO {
    DBConnection dBConnection;
    
    BookDAO book  = new BookDAO();
    My_Functions.Func_Class func = new Func_Class();
    
    public void addIssue(int book_id, int member_id, String status, String issue_date, String return_date, String note) {
        
        String insertQuery = "INSERT INTO `issue_book`(`book_id`, `member_id`, `status`, `issue_date`, `return_date`, `note`) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = dBConnection.getConnection().prepareStatement(insertQuery);
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
            PreparedStatement ps = dBConnection.getConnection().prepareStatement(updateQuery);
           
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
        BookDTO selected_book;
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
            ps = dBConnection.getConnection().prepareStatement("SELECT COUNT(*) AS total FROM `issue_book` WHERE `book_id` = ? and `status` = 'issued'");
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
            PreparedStatement ps = dBConnection.getConnection().prepareStatement(removeQuery);

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
