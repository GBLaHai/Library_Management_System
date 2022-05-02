/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Manh Hai
 */
public class MembersDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String gender;
    private byte[] picture;

    public MembersDTO() {
    }

    public MembersDTO(int id, String firstName, String lastName, String phoneNumber, String email, String gender, byte[] picture) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.picture = picture;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
    
    // functions
    // insert a new member function
    public void addMember(String firstName, String lastName, String phoneNumber, String email, String gender, byte[] picture) {
        
        String insertQuery = "INSERT INTO `members`(`firstName`, `lastName`, `phoneNumber`, `email`, `gender`, `picture`) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(insertQuery);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, phoneNumber);
            ps.setString(4, email);
            ps.setString(5, gender);
            ps.setBytes(6, picture);

            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Member added", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Member not added", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MembersDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // edit a member by id function
    public void editMember(int id, String firstName, String lastName, String phoneNumber, String email, String gender, byte[] picture) {
        
        String editQuery = "UPDATE `members` SET `firstName`=?,`lastName`=?,`phoneNumber`=?,`email`=?,`gender`=?,`picture`=? WHERE `id` = ?";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(editQuery);
            
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, phoneNumber);
            ps.setString(4, email);
            ps.setString(5, gender);
            ps.setBytes(6, picture);
            ps.setInt(7, id);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Member edited", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Member not edited", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MembersDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // remove a member by id function
    public void removeMember(int id) {
        
        String removeQuery = "DELETE FROM `members` WHERE `id` = ?";
        try {
            PreparedStatement ps = DB.getConnection().prepareStatement(removeQuery);

            ps.setInt(1, id);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Member deleted", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Member not deleted", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MembersDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // get Menber by ID
    public MembersDTO getMemberByID(int id) throws SQLException {
        Func_Class func = new Func_Class();
        String selectQuery = "SELECT * FROM `members` WHERE `id` = " + id;
        
        ResultSet rs = func.getData(selectQuery);
        
        if(rs.next()) {
            return new MembersDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBytes(7));
        } else {
            return null;
        }
    }
    
    // function to populate an arrayList with members
    public ArrayList<MembersDTO> memberList() {
        ArrayList<MembersDTO> mList = new ArrayList<>();
        
        String selectQuery = "SELECT * FROM `members`";
        ResultSet rs;
        
        DTO.Func_Class func = new Func_Class();
        
        try {
            
            rs = func.getData(selectQuery);
            
            MembersDTO member;
            
            while(rs.next()) {
                member = new MembersDTO(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("phoneNumber"), rs.getString("email"), rs.getString("gender"), rs.getBytes("picture"));
                mList.add(member);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MembersDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mList;
    }
    
    // function to populate an arrayList with members by
    public ArrayList<MembersDTO> memberListBy(String value) {
        ArrayList<MembersDTO> mList = new ArrayList<>();
        
        String selectQuery = "SELECT * FROM `members` WHERE `firstName` LIKE '%"+value+"%' OR `lastName` LIKE '%"+value+"%'";
        ResultSet rs;
        
        DTO.Func_Class func = new Func_Class();
        
        try {
            
            rs = func.getData(selectQuery);
            
            MembersDTO member;
            
            while(rs.next()) {
                member = new MembersDTO(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("phoneNumber"), rs.getString("email"), rs.getString("gender"), rs.getBytes("picture"));
                mList.add(member);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MembersDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mList;
    }
}
