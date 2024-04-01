package com.example.webd3102finalproject.dao;

import com.example.webd3102finalproject.model.Budget;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface BudgetDAO {
    int create(Budget budget) throws SQLException;

    List<Budget> selectAllByMonth(Date startDate, Date endDate, int userID) throws SQLException;

    int updateJunctionTable(int userID) throws SQLException;
}

