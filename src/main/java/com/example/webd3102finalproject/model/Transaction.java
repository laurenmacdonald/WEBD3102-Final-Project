package com.example.webd3102finalproject.model;

import java.sql.Date;

public class Transaction {
    private int transactionID;
    private int accountID;
    private int categoryID;
    private String transactionType;
    private double amount;
    private Date date;
    private String categoryName;
    private String accountName;


    public Transaction(String transactionType, double amount, Date date, String categoryName, String accountName) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
        this.categoryName = categoryName;
        this.accountName = accountName;
    }


    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
