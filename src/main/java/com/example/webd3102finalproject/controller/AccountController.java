package com.example.webd3102finalproject.controller;

import com.example.webd3102finalproject.dao.AccountDAO;
import com.example.webd3102finalproject.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.webd3102finalproject.controller.MySQLConnection.getConnection;

public class AccountController implements AccountDAO {
    private static final String SQL_INSERT = "INSERT INTO account (userID, accountName, startingBalance, createdDate) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT * FROM account WHERE userID = ?";
    private static final String SQL_BALANCE_AFTER = "SELECT a.accountName,  a.startingBalance, COALESCE(SUM(CASE WHEN t.transactionType = 'Income' THEN t.amount ELSE 0 END), 0) AS totalIncome, COALESCE(SUM(CASE WHEN t.transactionType = 'Expense' THEN t.amount ELSE 0 END), 0) AS totalExpense,  a.startingBalance + COALESCE(SUM(CASE WHEN t.transactionType = 'Income' THEN t.amount ELSE 0 END), 0) - COALESCE(SUM(CASE WHEN t.transactionType = 'Expense' THEN t.amount ELSE 0 END), 0) AS balanceAfter, a.createdDate FROM account a LEFT JOIN transaction t ON a.accountID = t.accountID WHERE a.userID = ? GROUP BY a.accountID, a.accountName, a.startingBalance, a.createdDate;";
    private static final String SQL_EQUATION = "SELECT a.accountName, a.startingBalance, SUM(CASE WHEN t.transactionType = 'Income' THEN t.amount ELSE 0 END) AS totalIncome, SUM(CASE WHEN t.transactionType = 'Expense' THEN t.amount ELSE 0 END) AS totalExpense, a.startingBalance + SUM(CASE WHEN t.transactionType = 'Income' THEN t.amount ELSE 0 END) - SUM(CASE WHEN t.transactionType = 'Expense' THEN t.amount ELSE 0 END) AS balanceAfter FROM account a JOIN transaction t ON a.accountID = t.accountID WHERE userID = ?;";

    @Override
    public int create(Account account) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try{
            conn = getConnection();
            preparedStatement = conn.prepareStatement(SQL_INSERT);
            preparedStatement.setInt(1, account.getUserID());
            preparedStatement.setString(2, account.getAccountName());
            preparedStatement.setDouble(3, account.getStartingBalance());
            preparedStatement.setDate(4, account.getCreatedDate());

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
    public List<Account> selectAll(int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Account> accountList = new ArrayList<>();

        try{
            conn = getConnection();
            preparedStatement = conn.prepareStatement(SQL_BALANCE_AFTER);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                accountList.add(new Account(
                        resultSet.getString("accountName"),
                        resultSet.getDouble("startingBalance"),
                        resultSet.getDouble("totalIncome"),
                        resultSet.getDouble("totalExpense"),
                        resultSet.getDouble("balanceAfter"),
                        resultSet.getDate("createdDate")
                ));
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
        return accountList;
    }
}
