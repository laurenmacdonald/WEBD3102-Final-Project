package com.example.webd3102finalproject.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/financial_planner";
        String uname = "root";
        String pass = "";
        return DriverManager.getConnection(url, uname, pass);
    }
}
