package com.example.webd3102finalproject.dao;

import com.example.webd3102finalproject.model.Budget;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface BudgetDAO {
    int create(Budget budget) throws SQLException;
    List<Budget> selectAllByMonth(int userID,  Date startDate, Date endDate) throws SQLException;
    int selectBudgetID(int categoryID) throws SQLException;
}

