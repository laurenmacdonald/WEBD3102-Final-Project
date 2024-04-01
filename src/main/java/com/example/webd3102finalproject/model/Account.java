package com.example.webd3102finalproject.model;

import java.sql.Date;

public class Account {
    private int accountID;
    private int userID;
    private String accountName;
    private double startingBalance;
    private double totalIncome;
    private double totalExpense;
    private double balanceAfter;
    private Date createdDate;

    public Account(int accountID, int userID, String accountName, double startingBalance, Date createdDate) {
        this.accountID = accountID;
        this.userID = userID;
        this.accountName = accountName;
        this.startingBalance = startingBalance;
        this.createdDate = createdDate;
    }

    public Account(int userID, String accountName, double startingBalance, Date createdDate) {
        this.userID = userID;
        this.accountName = accountName;
        this.startingBalance = startingBalance;
        this.createdDate = createdDate;
    }

    public Account(String accountName, double startingBalance, double totalIncome, double totalExpense, double balanceAfter, Date createdDate) {
        this.accountName = accountName;
        this.startingBalance = startingBalance;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balanceAfter = balanceAfter;
        this.createdDate = createdDate;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getStartingBalance() {
        return startingBalance;
    }

    public void setStartingBalance(double startingBalance) {
        this.startingBalance = startingBalance;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
