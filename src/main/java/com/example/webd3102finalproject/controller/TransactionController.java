package com.example.webd3102finalproject.controller;

import com.example.webd3102finalproject.dao.TransactionDAO;
import com.example.webd3102finalproject.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.webd3102finalproject.controller.MySQLConnection.getConnection;

public class TransactionController implements TransactionDAO {
    @Override
    public int create(Transaction transaction) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            conn = getConnection();
            String SQL_INSERT = "INSERT INTO transaction(accountID, categoryID, transactionType, amount, budgetID, date) VALUES (?, ?, ?, ?, ?, ?);";
            preparedStatement = conn.prepareStatement(SQL_INSERT);
            preparedStatement.setInt(1, transaction.getAccountID());
            preparedStatement.setInt(2, transaction.getCategoryID());
            preparedStatement.setString(3, transaction.getTransactionType());
            preparedStatement.setDouble(4, transaction.getAmount());
            preparedStatement.setInt(5, transaction.getBudgetID());
            preparedStatement.setDate(6, transaction.getDate());

            // returns 0 if unsuccessful, 1 if successful.
            result = preparedStatement.executeUpdate();
        } catch (SQLSyntaxErrorException ex) {
            System.err.println("Error: " + ex.getMessage());
        } catch (Exception genericException) {
            System.err.println("Exception:" + genericException.getMessage());
        } finally {
            assert preparedStatement != null;
            preparedStatement.close();
            conn.close();
        }
        return result;
    }

    @Override
    public List<Transaction> selectAllByType(String transactionType, Date startDate, Date endDate, int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Transaction> transactionByTypeList = new ArrayList<>();

        try {
            conn = getConnection();
            String SQL_SELECT_ALL_BY_TYPE = "SELECT t.transactionType, t.amount, t.date, c.categoryName, a.accountName FROM transaction t JOIN category c ON t.categoryID = c.categoryID JOIN account a ON t.accountID = a.accountID JOIN users u ON a.userID = u.userId WHERE transactionType = ? AND (date BETWEEN ? AND ?) AND u.userId = ?;";
            preparedStatement = conn.prepareStatement(SQL_SELECT_ALL_BY_TYPE);
            preparedStatement.setString(1, transactionType);
            preparedStatement.setDate(2, startDate);
            preparedStatement.setDate(3, endDate);
            preparedStatement.setInt(4, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transactionByTypeList.add(new Transaction(
                        resultSet.getString("transactionType"),
                        resultSet.getDouble("amount"),
                        resultSet.getDate("date"),
                        resultSet.getString("categoryName"),
                        resultSet.getString("accountName")
                ));
            }

        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return transactionByTypeList;
    }

    @Override
    public List<Transaction> selectAll(Date startDate, Date endDate, int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Transaction> transactionByTypeList = new ArrayList<>();

        try {
            conn = getConnection();
            String SQL_SELECT_ALL = "SELECT t.transactionType, t.amount, t.date, c.categoryName, a.accountName FROM transaction t JOIN category c ON t.categoryID = c.categoryID JOIN account a ON t.accountID = a.accountID JOIN users u ON a.userID = u.userId WHERE (date BETWEEN ? AND ?) AND u.userId = ? ORDER BY t.date DESC;";
            preparedStatement = conn.prepareStatement(SQL_SELECT_ALL);
            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);
            preparedStatement.setInt(3, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transactionByTypeList.add(new Transaction(
                        resultSet.getString("transactionType"),
                        resultSet.getDouble("amount"),
                        resultSet.getDate("date"),
                        resultSet.getString("categoryName"),
                        resultSet.getString("accountName")
                ));
            }

        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return transactionByTypeList;
    }

    @Override
    public HashMap<String, Double> expenseData(String transactionType, int userId, Date startDate, Date endDate) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HashMap<String, Double> expenseKeyValue = new HashMap<>();

        try {
            conn = getConnection();
            String SQL_SELECT_EXPENSES_WHERE = "SELECT c.categoryName, SUM(t.amount) AS totalAmount FROM category c JOIN transaction t ON c.categoryID = t.categoryID JOIN account a ON t.accountID = a.accountID WHERE t.transactionType = ? AND a.userID = ? AND t.date BETWEEN ? AND ? GROUP BY c.categoryName;";
            preparedStatement = conn.prepareStatement(SQL_SELECT_EXPENSES_WHERE);
            preparedStatement.setString(1, transactionType);
            preparedStatement.setInt(2, userId);
            preparedStatement.setDate(3, startDate);
            preparedStatement.setDate(4, endDate);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                expenseKeyValue.put(resultSet.getString("categoryName"), resultSet.getDouble("totalAmount"));
            }

        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return expenseKeyValue;
    }


}
