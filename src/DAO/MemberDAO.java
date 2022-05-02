/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.MemberDTO;
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
public class MemberDAO {
    DBConnection dBConnection;
    
    // functions
    // insert a new member function
    
    My_Functions.Func_Class func = new Func_Class();
    public void addMember(String firstName, String lastName, String phoneNumber, String email, String gender, byte[] picture) {
        
        String insertQuery = "INSERT INTO `members`(`firstName`, `lastName`, `phoneNumber`, `email`, `gender`, `picture`) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = dBConnection.getConnection().prepareStatement(insertQuery);
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
            Logger.getLogger(MemberDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // edit a member by id function
    public void editMember(int id, String firstName, String lastName, String phoneNumber, String email, String gender, byte[] picture) {
        
        String editQuery = "UPDATE `members` SET `firstName`=?,`lastName`=?,`phoneNumber`=?,`email`=?,`gender`=?,`picture`=? WHERE `id` = ?";
        try {
            PreparedStatement ps = dBConnection.getConnection().prepareStatement(editQuery);
            
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
            Logger.getLogger(MemberDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // remove a member by id function
    public void removeMember(int id) {
        
        String removeQuery = "DELETE FROM `members` WHERE `id` = ?";
        try {
            PreparedStatement ps = dBConnection.getConnection().prepareStatement(removeQuery);

            ps.setInt(1, id);
            
            if(ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Member deleted", "Notification", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Member not deleted", "Notification", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // get Menber by ID
    public MemberDTO getMemberByID(int id) throws SQLException {
        String selectQuery = "SELECT * FROM `members` WHERE `id` = " + id;
        
        ResultSet rs = func.getData(selectQuery);
        
        if(rs.next()) {
            return new MemberDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBytes(7));
        } else {
            return null;
        }
    }
    
    // function to populate an arrayList with members
    public ArrayList<MemberDTO> memberList() {
        ArrayList<MemberDTO> mList = new ArrayList<>();
        
        String selectQuery = "SELECT * FROM `members`";
        ResultSet rs;
        
        try {
            
            rs = func.getData(selectQuery);
            
            MemberDTO member;
            
            while(rs.next()) {
                member = new MemberDTO(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("phoneNumber"), rs.getString("email"), rs.getString("gender"), rs.getBytes("picture"));
                mList.add(member);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mList;
    }
    
    // function to populate an arrayList with members by
    public ArrayList<MemberDTO> memberListBy(String value) {
        ArrayList<MemberDTO> mList = new ArrayList<>();
        
        String selectQuery = "SELECT * FROM `members` WHERE `firstName` LIKE '%"+value+"%' OR `lastName` LIKE '%"+value+"%'";
        ResultSet rs;
        
        try {
            
            rs = func.getData(selectQuery);
            
            MemberDTO member;
            
            while(rs.next()) {
                member = new MemberDTO(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("phoneNumber"), rs.getString("email"), rs.getString("gender"), rs.getBytes("picture"));
                mList.add(member);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mList;
    }
}
