package com.example.webd3102finalproject.controller;

import com.example.webd3102finalproject.dao.BudgetDAO;
import com.example.webd3102finalproject.model.Budget;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.webd3102finalproject.controller.MySQLConnection.getConnection;

public class BudgetController implements BudgetDAO {
    @Override
    public int create(Budget budget) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try{
            conn = getConnection();
            String SQL_INSERT = "INSERT INTO budget (userId, categoryID, goalAmount, startDate, endDate) VALUES (?, ?, ?, ?, ?);";
            preparedStatement = conn.prepareStatement(SQL_INSERT);
            preparedStatement.setInt(1, budget.getUserID());
            preparedStatement.setInt(2, budget.getCategoryID());
            preparedStatement.setDouble(3, budget.getGoalAmount());
            preparedStatement.setDate(4, budget.getStartDate());
            preparedStatement.setDate(5, budget.getEndDate());

            // returns 0 if unsuccessful, 1 if successful.
            result = preparedStatement.executeUpdate();
        } catch (SQLSyntaxErrorException ex){
            System.err.println("Error: " + ex.getMessage());
        }catch (Exception genericException){
            System.err.println("Exception:" + genericException.getMessage());
        } finally {
            assert preparedStatement != null;
            preparedStatement.close();
            conn.close();
        }
        return result;
    }

    @Override
    public List<Budget> selectAllByMonth(int userID, Date startDate, Date endDate) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Budget> budgetList = new ArrayList<>();

        try{
            conn = getConnection();
            String SQL_SELECT_BY_MONTH = "SELECT b.budgetID, c.categoryName, b.goalAmount, COALESCE(SUM(CASE WHEN t.amount IS NULL THEN 0 ELSE t.amount END), 0) AS amountSpent, b.startDate, b.endDate FROM budget b JOIN category c ON b.categoryID = c.categoryID LEFT JOIN transaction t ON t.budgetID = b.budgetID AND t.date BETWEEN b.startDate AND b.endDate WHERE b.userID = ? AND startDate = ? AND endDate = ? GROUP BY b.budgetID, c.categoryName, b.goalAmount, b.startDate, b.endDate;";
            preparedStatement = conn.prepareStatement(SQL_SELECT_BY_MONTH);
            preparedStatement.setInt(1, userID);
            preparedStatement.setDate(2, startDate);
            preparedStatement.setDate(3, endDate);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                budgetList.add(new Budget(
                        resultSet.getInt("budgetID"),
                        resultSet.getDouble("goalAmount"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getDouble("amountSpent"),
                        resultSet.getString("categoryName")
                ));
            }

        } catch(SQLException ex) {
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
        return budgetList;
    }


    @Override
    public int selectBudgetID(int categoryID) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int budgetID = 0;

        try{
            conn = getConnection();
            String SQL_SELECT_ID = "SELECT budgetID FROM `budget` WHERE categoryID = ?;";
            preparedStatement = conn.prepareStatement(SQL_SELECT_ID);
            preparedStatement.setInt(1, categoryID);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                        budgetID = resultSet.getInt("budgetID");
            }

        } catch(SQLException ex) {
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
        return budgetID;
    }
}
