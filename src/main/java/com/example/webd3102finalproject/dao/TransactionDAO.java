package com.example.webd3102finalproject.dao;

import com.example.webd3102finalproject.model.Transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface TransactionDAO {
    int create(Transaction transaction) throws SQLException;
    List<Transaction> selectAllByType(String transactionType, Date startDate, Date endDate, int userId) throws SQLException;
    List<Transaction> selectAll(Date startDate, Date endDate, int userId) throws SQLException;
    HashMap<String, Double> expenseData(String transactionType, int userId) throws SQLException;

}
