package com.example.webd3102finalproject.dao;

import com.example.webd3102finalproject.model.User;

import java.sql.SQLException;

/**
 * Data access object (DAO) interface for the user data to be implemented in associated table class
 */
public interface UserDAO {
    // Create a new user and get the information required for login.
    int create(User user) throws SQLException;
    User select(String email) throws SQLException;
}