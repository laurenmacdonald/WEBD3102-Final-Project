package com.example.webd3102finalproject.controller;

import java.sql.*;
import com.example.webd3102finalproject.dao.UserDAO;
import com.example.webd3102finalproject.model.User;

import static com.example.webd3102finalproject.controller.MySQLConnection.getConnection;

public class UserController implements UserDAO {
    private static final String SQL_SELECT = "SELECT * FROM users WHERE email = ?";
    private static final String SQL_INSERT = "INSERT INTO users (firstName, lastName, email, passwordHash) VALUES (?, ?, ?, ?);";

    @Override
    public int create(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try{
            conn = getConnection();
            preparedStatement = conn.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());

            // returns 0 if unsuccessful, 1 if successful.
            result = preparedStatement.executeUpdate();
        } catch (SQLSyntaxErrorException ex){
            System.err.println("Error: " + ex.getMessage());
        }catch (Exception genericException){
            System.err.println("Exception:" + genericException.getMessage());
        } finally {
            preparedStatement.close();
            conn.close();
        }
        return result;
    }

    @Override
    public User select(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try{
            conn = getConnection();
            preparedStatement = conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                user = new User(
                        resultSet.getInt("userId"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("passwordHash")
                );
            }
        } catch(SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        } finally {
            if(resultSet != null){ resultSet.close(); }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if(conn != null){
                conn.close();
            }
        }
        return user;
    }
}