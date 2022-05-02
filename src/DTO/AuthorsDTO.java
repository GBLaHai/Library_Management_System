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
public class AuthorsDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String field_Of_Expertise;
    private String about;

    public AuthorsDTO() {
    }

    public AuthorsDTO(int id, String firstName, String lastName, String field_Of_Expertise, String about) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.field_Of_Expertise = field_Of_Expertise;
        this.about = about;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getField_Of_Expertise() {
        return field_Of_Expertise;
    }

    public void setField_Of_Expertise(String field_Of_Expertise) {
        this.field_Of_Expertise = field_Of_Expertise;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
    
    // functions
    
    DTO.Func_Class func = new Func_Class();
    // insert a new author function
    public void addAuthor(String firstName, String lastName, String expertise, String about) {
        
        String insertQuery = "INSERT INTO `authors`(`firstName`, `lastName`, `expertise`, `about`) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(insertQuery);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, expertise);
            ps.setString(4, about);
            
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Author added", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Author not added", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthorsDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // edit a author by id function
    public void editAuthor(int id, String firstName, String lastName, String expertise, String about) {
        
        String editQuery = "UPDATE `authors` SET `firstName`= ?,`lastName`= ?,`expertise`= ?,`about`= ? WHERE `id` = ?";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(editQuery);
            
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, expertise);
            ps.setString(4, about);
            ps.setInt(5, id);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Author edited", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Author not edited", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthorsDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // remove a author by id function
    public void removeAuthor(int id) {
        
        String removeQuery = "DELETE FROM `authors` WHERE `id` = ?";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(removeQuery);

            ps.setInt(1, id);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Author deleted", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Author not deleted", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthorsDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // function to populate an arrayList with authors
    public ArrayList<AuthorsDTO> authorList() {
        ArrayList<AuthorsDTO> aList = new ArrayList<>();
        
        String selectQuery = "SELECT * FROM `authors`";
        ResultSet rs;
        
        try {
            
            rs = func.getData(selectQuery);
            
            AuthorsDTO author;
            
            while(rs.next()) {
                author = new AuthorsDTO(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("expertise"), rs.getString("about"));
                aList.add(author);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthorsDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return aList;
    }
    
    // create the function to get author by id
    public AuthorsDTO getAuthorByID(int id) {
        String selectQuery = "SELECT * FROM `authors` WHERE `id` = " + id;
        ResultSet rs;
        AuthorsDTO author = null;
        
        try {
            
            rs = func.getData(selectQuery);
            
            while(rs.next()) {
                author = new AuthorsDTO(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("expertise"), rs.getString("about"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthorsDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return author;
    }
}
