/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manh Hai
 */
public class DB {
    // create the connnection between the project and mysql database
    // 1 - download the java-mysql connector
    // 2 - add the jar file to the project
    private static String serverName = "localhost";
    private static String userName = "root";
    private static String dbName = "java_library_system";
    private static int portNumber = 3306;
    private static String pass = ""; // no password
    
    // create the function to create and return the connection
    public static Connection getConnection() 
    {
        Connection connection = null;
        
        MysqlDataSource datasource = new MysqlDataSource();
        datasource.setServerName(serverName);
        datasource.setUser(userName);
        datasource.setDatabaseName(dbName);
        datasource.setPortNumber(portNumber);
        datasource.setPassword(pass);
        
        try {
            connection = (Connection) datasource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connection;
    }
}
