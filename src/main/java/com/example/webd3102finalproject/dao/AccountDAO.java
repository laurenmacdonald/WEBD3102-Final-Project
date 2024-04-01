package com.example.webd3102finalproject.dao;

import com.example.webd3102finalproject.model.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDAO {
    int create(Account account) throws SQLException;
    List<Account> selectAll(int userId) throws SQLException;
}
