/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package My_Classes;

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
public class Users {
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String userType; // admin or simple user

    public Users() {
    }

    public Users(int id, String firstName, String lastName, String userName, String password, String userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    My_Classes.Func_Class func = new Func_Class();
    // insert a new user function
    public void addUser (String firstName, String lastName, String userName, String password, String userType) {
        
        String insertQuery = "INSERT INTO `users_table`(`firstName`, `lastName`, `userName`, `password`, `user_type`) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(insertQuery);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, userName);
            ps.setString(4, password);
            ps.setString(5, userType);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "User added", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "User not added", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // create a function to edit a user by id
    public void editAuthor(int id, String firstName, String lastName, String userName, String password, String userType) {
        
        String editQuery = "UPDATE `users_table` SET `firstName`=?,`lastName`=?,`userName`=?,`password`=?,`user_type`=? WHERE `id`=?";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(editQuery);
            
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, userName);
            ps.setString(4, password);
            ps.setString(5, userType);
            ps.setInt(6, id);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "User edited", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "User not edited", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // create a function to remove a user by id
    public void removeUser(int id) {
        
        String removeQuery = "DELETE FROM `users_table` WHERE `id` = ?";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(removeQuery);

            ps.setInt(1, id);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "User deleted", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "User not deleted", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // create the function to check if username already exist
    public boolean checkUserNameExsit(int id, String username) {
        String selectQuery = "SELECT * FROM `users_table` WHERE `userName`='" + username + "' AND id <> " + id;
        ResultSet rs;
        boolean exist = false;
        try {
            
            rs = func.getData(selectQuery);
            
            while(rs.next()) {
                exist = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exist;
    }
    
    // function to populate an arrayList with users
    public ArrayList<Users> userList() {
        ArrayList<Users> uList = new ArrayList<>();
        
        String selectQuery = "SELECT * FROM `users_table`";
        ResultSet rs;
        
        try {
            
            rs = func.getData(selectQuery);
            
            Users user;
            
            while(rs.next()) {
                // `id`, `firstName`, `lastName`, `userName`, `password`, `user_type`
                user = new Users(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("userName"), rs.getString("password"), rs.getString("user_type"));
                uList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return uList;
    }
    
    // create a function to allow the user login
    public Users tryLogin(String username, String password) {
        String selectQuery = "SELECT * FROM `users_table` WHERE `userName`='"+username+"' AND `password`='"+password+"'";
        ResultSet rs;
        Users user = null;
        
        try {
            
            rs = func.getData(selectQuery);
            
            while(rs.next()) {
                
                //`id`, `firstName`, `lastName`, `userName`, `password`, `user_type`
                user = new Users(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("userName"), rs.getString("password"), rs.getString("user_type"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return user;
    }
}
