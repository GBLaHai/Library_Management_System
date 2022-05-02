/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.GenreDTO;
import My_Functions.Func_Class;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Manh Hai
 */
public class GenreDAO {
    DBConnection dBConnection;
    
    // functions
    
    My_Functions.Func_Class func = new Func_Class();
    // insert a new genre function
    public void addGenre(String name) {
        
        String insertQuery = "INSERT INTO `book_genres` (`name`) VALUES (?)";
        try {
            PreparedStatement ps = dBConnection.getConnection().prepareStatement(insertQuery);
            ps.setString(1, name);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Genre added", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Genre not added", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenreDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // edit a genre by id function
    public void editGenre(int id, String name) {
        
        String editQuery = "UPDATE `book_genres` SET `name`= ? WHERE `id` = ?";
        try {
            PreparedStatement ps = dBConnection.getConnection().prepareStatement(editQuery);
            
            ps.setString(1, name);
            ps.setInt(2, id);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Genre edited", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Genre not edited", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenreDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // remove a genre by id function
    public void removeGenre(int id) {
        
        String removeQuery = "DELETE FROM `book_genres` WHERE `id` = ?";
        try {
            PreparedStatement ps = dBConnection.getConnection().prepareStatement(removeQuery);

            ps.setInt(1, id);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Genre deleted", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Genre not deleted", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenreDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // function to populate an arrayList with genres
    public ArrayList<GenreDTO> genreList() {
        ArrayList<GenreDTO> gList = new ArrayList<>();
        
        String selectQuery = "SELECT * FROM `book_genres`";
        ResultSet rs;
        
        try {
            rs = func.getData(selectQuery);
            
            GenreDTO genre;
            
            while(rs.next()) {
                genre = new GenreDTO(rs.getInt("id"), rs.getString("name"));
                gList.add(genre);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenreDTO.class.getName()).log(Level.SEVERE, null, ex);
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
            
            GenreDTO genre;
            
            while(rs.next()) {
                genre = new GenreDTO(rs.getInt("id"), rs.getString("name"));
                map.put(genre.getName(), genre.getId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenreDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return map;
    }
    
    public GenreDTO getGenreByID(int id) {
        String selectQuery = "SELECT * FROM `book_genres` WHERE `id` = " + id;
        ResultSet rs;
        GenreDTO genre = null;
        
        try {
            
            rs = func.getData(selectQuery);
            
            while(rs.next()) {
                genre = new GenreDTO(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenreDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return genre;
    }
}
