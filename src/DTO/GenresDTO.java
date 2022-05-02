/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 *
 * @author Manh Hai
 */
public class GenresDTO {
    private int id;
    private String name;

    public GenresDTO() {
    }

    public GenresDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // functions
    
    DTO.Func_Class func = new Func_Class();
    // insert a new genre function
    public void addGenre(String name) {
        
        String insertQuery = "INSERT INTO `book_genres` (`name`) VALUES (?)";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(insertQuery);
            ps.setString(1, name);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Genre added", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Genre not added", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenresDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // edit a genre by id function
    public void editGenre(int id, String name) {
        
        String editQuery = "UPDATE `book_genres` SET `name`= ? WHERE `id` = ?";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(editQuery);
            
            ps.setString(1, name);
            ps.setInt(2, id);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Genre edited", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Genre not edited", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenresDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // remove a genre by id function
    public void removeGenre(int id) {
        
        String removeQuery = "DELETE FROM `book_genres` WHERE `id` = ?";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(removeQuery);

            ps.setInt(1, id);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Genre deleted", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Genre not deleted", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenresDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // function to populate an arrayList with genres
    public ArrayList<GenresDTO> genreList() {
        ArrayList<GenresDTO> gList = new ArrayList<>();
        
        String selectQuery = "SELECT * FROM `book_genres`";
        ResultSet rs;
        
        try {
            rs = func.getData(selectQuery);
            
            GenresDTO genre;
            
            while(rs.next()) {
                genre = new GenresDTO(rs.getInt("id"), rs.getString("name"));
                gList.add(genre);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenresDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return gList;
    }
    
    // create a function to return a hashmap
    // string is a key
    // integer is a value
    
    public HashMap<String, Integer> getGenresMap() {
        HashMap<String, Integer> map = new HashMap<>();
        
        String selectQuery = "SELECT * FROM `book_genres`";
        ResultSet rs;
        
        try {
            rs = func.getData(selectQuery);
            
            GenresDTO genre;
            
            while(rs.next()) {
                genre = new GenresDTO(rs.getInt("id"), rs.getString("name"));
                map.put(genre.getName(), genre.getId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenresDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return map;
    }
    
    public GenresDTO getGenreByID(int id) {
        String selectQuery = "SELECT * FROM `book_genres` WHERE `id` = " + id;
        ResultSet rs;
        GenresDTO genre = null;
        
        try {
            
            rs = func.getData(selectQuery);
            
            while(rs.next()) {
                genre = new GenresDTO(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenresDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return genre;
    }
}
