package com.example.webd3102finalproject.model;

import java.sql.Date;

public class Budget {
    // From budget table
    private int budgetID;
    private int userID;
    private int categoryID;
    private double goalAmount;
    private Date startDate;
    private Date endDate;
    // From BudgetTransaction table
    private int budgetTransactionID;
    private int transactionID;
    private double actualAmount;
    // String values for ids
    private String categoryName;
    // From transaction table
    private double transactionAmount;

    public Budget(int userID, int categoryID, double goalAmount, Date startDate, Date endDate){
        this.userID = userID;
        this.categoryID = categoryID;
        this.goalAmount = goalAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    // Constructor for budget table
//    public Budget(int budgetID, int categoryID, double goalAmount, Date startDate, Date endDate) {
//        this.budgetID = budgetID;
//        this.categoryID = categoryID;
//        this.goalAmount = goalAmount;
//        this.startDate = startDate;
//        this.endDate = endDate;
//    }

    public Budget(int budgetID, int categoryID, double goalAmount, Date startDate, Date endDate, int budgetTransactionID, int transactionID, double actualAmount, String categoryName) {
        this.budgetID = budgetID;
        this.categoryID = categoryID;
        this.goalAmount = goalAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budgetTransactionID = budgetTransactionID;
        this.transactionID = transactionID;
        this.actualAmount = actualAmount;
        this.categoryName = categoryName;
    }

    public Budget(double goalAmount, Date startDate, Date endDate, double actualAmount, String categoryName, double transactionAmount) {
        this.goalAmount = goalAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.actualAmount = actualAmount;
        this.categoryName = categoryName;
        this.transactionAmount = transactionAmount;
    }

    public int getBudgetID() {
        return budgetID;
    }

    public void setBudgetID(int budgetID) {
        this.budgetID = budgetID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public double getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(double goalAmount) {
        this.goalAmount = goalAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getBudgetTransactionID() {
        return budgetTransactionID;
    }

    public void setBudgetTransactionID(int budgetTransactionID) {
        this.budgetTransactionID = budgetTransactionID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
