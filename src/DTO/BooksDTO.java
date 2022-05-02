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
import java.sql.Statement;
import javax.swing.JLabel;

/**
 *
 * @author Manh Hai
 */
public class BooksDTO {
    private int id;
    private String isbn;
    private String name;
    private int author_id;
    private int genre_id;
    private int quantity;
    private String publisher;
    private double price;
    private String date_received;
    private String description;
    private byte[] cover;

    public BooksDTO() {
    }

    public BooksDTO(int id, String isbn, String name, int author_id, int genre_id, int quantity, String publisher, double price, String date_received, String description, byte[] cover) {
        this.id = id;
        this.isbn = isbn;
        this.name = name;
        this.author_id = author_id;
        this.genre_id = genre_id;
        this.quantity = quantity;
        this.publisher = publisher;
        this.price = price;
        this.date_received = date_received;
        this.description = description;
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate_received() {
        return date_received;
    }

    public void setDate_received(String date_received) {
        this.date_received = date_received;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }
   
    
    Func_Class func = new Func_Class();
    // insert a new book function
    public void addBook(String isbn, String name, int author_id, int genre_id, int quantity, String publisher, double price, String date_received, String description, byte[] cover) {
        
        String insertQuery = "INSERT INTO `books`(`isbn`, `name`, `author_id`, `genre_id`, `quantity`, `publisher`, `price`, `date_received`, `description`, `cover_image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(insertQuery);
            ps.setString(1, isbn);
            ps.setString(2, name);
            ps.setInt(3, author_id);
            ps.setInt(4, genre_id);
            ps.setInt(5, quantity);
            ps.setString(6, publisher);
            ps.setDouble(7, price);
            ps.setString(8, date_received);
            ps.setString(9, description);
            ps.setBytes(10, cover);

            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Book added", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Book not added", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BooksDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // insert a new book function
    public void editBook(String isbn, String name, int author_id, int genre_id, int quantity, String publisher, double price, String date_received, String description, byte[] cover) {
        
        String editQuery = "";
        if(cover != null) { // if you want to update image
            editQuery = "UPDATE `books` SET `name`=? ,`author_id`=? ,`genre_id`=? ,`quantity`=? ,`publisher`=? ,`price`=? ,`date_received`=? ,`description`=? ,`cover_image`=? WHERE `isbn` =?";
            try {
                PreparedStatement ps = DB.getConnection().prepareStatement(editQuery);
                ps.setString(1, name);
                ps.setInt(2, author_id);
                ps.setInt(3, genre_id);
                ps.setInt(4, quantity);
                ps.setString(5, publisher);
                ps.setDouble(6, price);
                ps.setString(7, date_received);
                ps.setString(8, description);
                ps.setBytes(9, cover);
                ps.setString(10, isbn);

                if(ps.executeUpdate() != 0) {
                    JOptionPane.showMessageDialog(null, "Book edited", "Notification", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Book not edited", "Notification", 2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(BooksDTO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else { // if not
            editQuery = "UPDATE `books` SET `name`=? ,`author_id`=? ,`genre_id`=? ,`quantity`=? ,`publisher`=? ,`price`=? ,`date_received`=? ,`description`=? WHERE `isbn` =?";
            try {
                PreparedStatement ps = DB.getConnection().prepareStatement(editQuery);
                ps.setString(1, name);
                ps.setInt(2, author_id);
                ps.setInt(3, genre_id);
                ps.setInt(4, quantity);
                ps.setString(5, publisher);
                ps.setDouble(6, price);
                ps.setString(7, date_received);
                ps.setString(8, description);
                //ps.setBytes(9, cover);
                ps.setString(9, isbn);

                if(ps.executeUpdate() != 0) {
                    JOptionPane.showMessageDialog(null, "Book edited", "Notification", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Book not added", "Notification", 2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(BooksDTO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // remove a book by id function
    public void removeBook(String isbn) {
        
        String removeQuery = "DELETE FROM `books` WHERE `isbn` = ?";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(removeQuery);

            ps.setString(1, isbn);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Book deleted", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Book not deleted", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BooksDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // create the function to check if isbn already exists
    public boolean checkISBNexists(String isbn) {
        String query = "SELECT * FROM `books` WHERE `isbn` = '"+ isbn +"'";
        
        ResultSet rs = func.getData(query);
        
        try {
            if(rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BooksDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    public BooksDTO searchBookByISBN(String _isbn) {
        String query = "SELECT * FROM `books` WHERE `isbn` = '"+ _isbn +"'";
        
        ResultSet rs = func.getData(query);
        BooksDTO book = null;
        
        try {
            if(rs.next()) {
                int id = rs.getInt(1);
                String isbn = rs.getString(2);
                String name = rs.getString(3);
                int author_id = rs.getInt(4);
                int genre_id = rs.getInt(5);
                int quantity = rs.getInt(6);
                String publisher = rs.getString(7);
                double price = rs.getDouble(8);
                String date_received = rs.getString(9);
                String description = rs.getString(10);
                byte[] cover = rs.getBytes(11);
                
                book = new BooksDTO(id, isbn, name, author_id, genre_id, quantity, publisher, price, date_received, description, cover);
            } else {
                return book;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BooksDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return book;
    }
    
    // function to populate an arrayList with books
    public ArrayList<BooksDTO> bookList() {
        ArrayList<BooksDTO> bList = new ArrayList<>();
        
        String selectQuery = "SELECT * FROM `books`";
        ResultSet rs;
        
        try {
            
            rs = func.getData(selectQuery);
            
            BooksDTO book;
            
            while(rs.next()) {
                int id = rs.getInt(1);
                String isbn = rs.getString(2);
                String name = rs.getString(3);
                int author_id = rs.getInt(4);
                int genre_id = rs.getInt(5);
                int quantity = rs.getInt(6);
                String publisher = rs.getString(7);
                double price = rs.getDouble(8);
                String date_received = rs.getString(9);
                String description = rs.getString(10);
                byte[] cover = rs.getBytes(11);
                
                book = new BooksDTO(id, isbn, name, author_id, genre_id, quantity, publisher, price, date_received, description, cover);
                bList.add(book);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BooksDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bList;
    }
    
    // get BooksDTO by ID
    public BooksDTO getBookByID(int _id) throws SQLException {

        String selectQuery = "SELECT * FROM `books` WHERE `id` = " + _id;
        
        ResultSet rs = func.getData(selectQuery);
        
        if(rs.next()) {
                int id = rs.getInt(1);
                String isbn = rs.getString(2);
                String name = rs.getString(3);
                int author_id = rs.getInt(4);
                int genre_id = rs.getInt(5);
                int quantity = rs.getInt(6);
                String publisher = rs.getString(7);
                double price = rs.getDouble(8);
                String date_received = rs.getString(9);
                String description = rs.getString(10);
                byte[] cover = rs.getBytes(11);
                
                BooksDTO book = new BooksDTO(id, isbn, name, author_id, genre_id, quantity, publisher, price, date_received, description, cover);
                
                return book;
        } else {
            return null;
        }
    }
    
    // function to populate an arrayList with books by
    public ArrayList<BooksDTO> bookListByName(String value) {
        ArrayList<BooksDTO> bList = new ArrayList<>();
        
        String selectQuery = "SELECT * FROM `books` WHERE `name` LIKE '%"+value+"%' OR `description` LIKE '%"+value+"%'";
        ResultSet rs;
        
        DTO.Func_Class func = new Func_Class();
        
        try {
            
            rs = func.getData(selectQuery);
            
            BooksDTO book;
            
            while(rs.next()) {
                int id = rs.getInt(1);
                String isbn = rs.getString(2);
                String name = rs.getString(3);
                int author_id = rs.getInt(4);
                int genre_id = rs.getInt(5);
                int quantity = rs.getInt(6);
                String publisher = rs.getString(7);
                double price = rs.getDouble(8);
                String date_received = rs.getString(9);
                String description = rs.getString(10);
                byte[] cover = rs.getBytes(11);
                
                book = new BooksDTO(id, isbn, name, author_id, genre_id, quantity, publisher, price, date_received, description, cover);
                bList.add(book);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BooksDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bList;
    }
    
    public ArrayList<BooksDTO> bookListByDate(String dateFrom, String dateTo) {
        ArrayList<BooksDTO> bList = new ArrayList<>();
        
        String selectQuery = "SELECT * FROM `books` WHERE `date_received` BETWEEN '"+ dateFrom + "' AND '" + dateTo + "'";
        ResultSet rs;
        
        DTO.Func_Class func = new Func_Class();
        
        try {
            
            rs = func.getData(selectQuery);
            
            BooksDTO book;
            
            while(rs.next()) {
                int id = rs.getInt(1);
                String isbn = rs.getString(2);
                String name = rs.getString(3);
                int author_id = rs.getInt(4);
                int genre_id = rs.getInt(5);
                int quantity = rs.getInt(6);
                String publisher = rs.getString(7);
                double price = rs.getDouble(8);
                String date_received = rs.getString(9);
                String description = rs.getString(10);
                byte[] cover = rs.getBytes(11);
                
                book = new BooksDTO(id, isbn, name, author_id, genre_id, quantity, publisher, price, date_received, description, cover);
                bList.add(book);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BooksDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bList;
    }
   
    // create the function to display the lastest 5 books
    public void displayBooksCover(JLabel[] lables_tab) {
        ResultSet rs;
        Statement st;
        
        try {
            st = DB.getConnection().createStatement();
            rs = st.executeQuery("SELECT `cover_image` FROM `books` ORDER BY `id` LIMIT 5");
            byte[] image;
            int i = 0;
            while(rs.next()) {
                image = rs.getBytes("cover_image");
                func.displayImageByBytes(lables_tab[i].getWidth(), lables_tab[i].getHeight(), image, lables_tab[i]);
                ++i;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BooksDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setQuantity_Minus_One(int book_id ,int quantity) {
        
        String updateQuantityQuery = "UPDATE `books` SET `quantity`=? WHERE `id` =?";
            try {
                PreparedStatement ps = DB.getConnection().prepareStatement(updateQuantityQuery);
                
                ps.setInt(1, quantity);
                ps.setInt(2, book_id);

                if(ps.executeUpdate() != 0) {
                    JOptionPane.showMessageDialog(null, "The Book quantity was updated", "Notification", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "the Book quantity can not updated", "Notification", 2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(BooksDTO.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
