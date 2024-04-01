package com.example.webd3102finalproject.controller;

import com.example.webd3102finalproject.dao.BudgetDAO;
import com.example.webd3102finalproject.model.Budget;
import com.example.webd3102finalproject.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.webd3102finalproject.controller.MySQLConnection.getConnection;

public class BudgetController implements BudgetDAO {
    private final String SQL_INSERT = "INSERT INTO budget (userId, categoryID, goalAmount, startDate, endDate) VALUES (?, ?, ?, ?, ?);";
    private final String SQL_INSERT_JUNCTION = "INSERT INTO budgettransaction (budgetID, transactionID, actualAmount) SELECT b.budgetID, t.transactionID, (b.goalAmount - t.amount) AS actualAmount FROM budget b JOIN transaction t ON t.date BETWEEN b.startDate AND b.endDate WHERE b.userID = ?;";
    private final String SQL_SELECT_BY_MONTH = "SELECT CASE WHEN bt.actualAmount IS NULL THEN b.goalAmount ELSE bt.actualAmount END AS actualAmount, b.goalAmount, b.startDate, b.endDate, c.categoryName, CASE WHEN t.amount IS NULL THEN 0 ELSE t.amount END AS amount FROM budgettransaction bt RIGHT JOIN budget b ON bt.budgetID = b.budgetID JOIN category c ON b.categoryID = c.categoryID LEFT JOIN transaction t ON bt.transactionID = t.transactionID WHERE b.startDate = ? AND b.endDate = ? AND b.userId = ?;";
    @Override
    public int create(Budget budget) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try{
            conn = getConnection();
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
            preparedStatement.close();
            conn.close();
        }
        return result;
    }

    @Override
    public List<Budget> selectAllByMonth(Date startDate, Date endDate, int userID) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Budget> budgetList = new ArrayList<>();

        try{
            conn = getConnection();
            preparedStatement = conn.prepareStatement(SQL_SELECT_BY_MONTH);
            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);
            preparedStatement.setInt(3, userID);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                budgetList.add(new Budget(
                        resultSet.getDouble("goalAmount"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getDouble("actualAmount"),
                        resultSet.getString("categoryName"),
                        resultSet.getDouble("amount")
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
    public int updateJunctionTable(int userID) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try{
            conn = getConnection();
            preparedStatement = conn.prepareStatement(SQL_INSERT_JUNCTION);
            preparedStatement.setInt(1, userID);

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
}
