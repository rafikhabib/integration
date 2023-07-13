/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Anis
 */
public class DataSource {

 
    
    private Connection cnx;
    
    private static DataSource   instance;
    private final String url = "jdbc:mysql://localhost:3306/esprit";
    private final String user = "root";
    private final String password = "";
    
    private DataSource() {
    try {
    cnx = DriverManager.getConnection(url, user, password);
        System.out.println("Vous êtes connecté");
    }
    catch(SQLException e){
        System.out.println(e.getMessage());
    }
    
}
    public static DataSource GetInstance(){
        if (instance == null)
            instance = new DataSource();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
    
    
    
}
