package com.example.webd3102finalproject;

import com.example.webd3102finalproject.controller.AccountController;
import com.example.webd3102finalproject.controller.BudgetController;
import com.example.webd3102finalproject.controller.TransactionController;
import com.example.webd3102finalproject.model.Account;
import com.example.webd3102finalproject.model.Budget;
import com.example.webd3102finalproject.model.Transaction;
import com.example.webd3102finalproject.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = "/", loadOnStartup = 1, name="UserDataServlet")
public class UserDataServlet extends HttpServlet {

    private AccountController accountController;
    private TransactionController transactionController;
    private BudgetController budgetController;
    public void init() {
        accountController = new AccountController();
        transactionController = new TransactionController();
        budgetController = new BudgetController();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;
        String action = request.getServletPath();
        if(action.equals("/dashboard")){
            try {
                getTotalWealth(request,response);
                getListOfAllTransactions(request, response);
                getTransactionsByType(request,response);
                getListOfBudgets(request, response);
                getArrayOfExpenseSummaryData(request);
                createListOfBackgroundImages(request);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            dispatcher = request.getRequestDispatcher("/dashboard.jsp");
            dispatcher.forward(request, response);
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getServletPath();
        switch(action){
            case "/add-account":
                addAccount(request, response);
                break;
            case "/add-budget":
                addBudget(request, response);
                break;
        }
    }

    public void addAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        String accountType = request.getParameter("accountType");
        Date createdDate = Date.valueOf(request.getParameter("createdDate"));
        double startingBalance = Double.parseDouble(request.getParameter("startingBalance"));
        Account account = new Account(userId, accountType, startingBalance, createdDate);
        try {
            // If successful, set a session attribute to account object.
            if (accountController.create(account) == 1) {
                session.setAttribute("account", account);
                response.sendRedirect(request.getContextPath() + "/dashboard");
                // if unsuccessful, display error message.
            } else {
                String errorMessage = "Error adding account.";
                session.setAttribute("accountAdditionError", errorMessage);
                response.sendRedirect(request.getContextPath() + "/dashboard");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addBudget(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        int categoryId = Integer.parseInt(request.getParameter("category"));
        double goalAmount = Double.parseDouble(request.getParameter("goalAmount"));
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        Budget budget = new Budget(userId, categoryId, goalAmount, startDate, endDate);
        try{
            if(budgetController.create(budget) == 1) {
                response.sendRedirect(request.getContextPath() + "/dashboard");
            }
            else {
                String budgetErrorMessage = "Error adding budget.";
                session.setAttribute("budgetAdditionError", budgetErrorMessage);
                response.sendRedirect(request.getContextPath() + "/dashboard");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void getTotalWealth(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        // Get user ID from the session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        // Get list of all account information for the user.
        List<Account> accountList = accountController.selectAll(userId);
        if (accountList != null) {
            double totalWealth = 0.0;
            // Add all the account balances together to get total wealth
            for (Account allAccountBalance : accountList) {
                totalWealth += allAccountBalance.getBalanceAfter();
            }
            session.setAttribute("totalWealth", totalWealth);
            session.setAttribute("accountList", accountList);
        } else {
            String noAccountMessage = "No accounts. Add one to continue.";
            response.sendRedirect(request.getContextPath() + "/dashboard?noAccounts=" + URLEncoder.encode(noAccountMessage, StandardCharsets.UTF_8));
        }
    }

    private void getTransactionsByType(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        // Get user ID from the session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        // Set up date values
        String startDateStr = "2024-03-01";
        String endDateStr = "2024-03-31";
        Date startDate = Date.valueOf(startDateStr);
        Date endDate = Date.valueOf(endDateStr);
        // Create list of transactions
        List<Transaction> incomeTransactions = transactionController.selectAllByType("Income", startDate, endDate, userId);
        List<Transaction> expenseTransactions = transactionController.selectAllByType("Expense", startDate, endDate, userId);
        if (incomeTransactions != null) {
            double totalIncome = 0.0;
            // Add all the account balances together to get total wealth
            for (Transaction incomeTransaction : incomeTransactions) {
                totalIncome += incomeTransaction.getAmount();
            }
            session.setAttribute("totalIncome", totalIncome);
            session.setAttribute("incomeTransactions", incomeTransactions);
        }
        if (expenseTransactions != null) {
            double totalExpense = 0.0;
            // Add all the account balances together to get total wealth
            for (Transaction expenseTransaction : expenseTransactions) {
                totalExpense += expenseTransaction.getAmount();
            }
            session.setAttribute("totalExpense", totalExpense);
            session.setAttribute("expenseTransactions", expenseTransactions);

        }
    }
    void getListOfAllTransactions(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        // Get user ID from the session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        // Set up date values
        String startDateStr = "2024-03-01";
        String endDateStr = "2024-03-31";
        Date startDate = Date.valueOf(startDateStr);
        Date endDate = Date.valueOf(endDateStr);
        // Create list of transactions
        List<Transaction> transactionList = transactionController.selectAll(startDate, endDate, userId);
        session.setAttribute("transactionList", transactionList);
    }
    void getArrayOfExpenseSummaryData(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        HashMap<String, Double> expenseData = transactionController.expenseData("Expense", userId);
        String[] expenseDataKeys = expenseData.keySet().toArray(new String[0]);
        Double[] expenseDataValues = expenseData.values().toArray(new Double[0]);
        session.setAttribute("expenseDataKeys", expenseDataKeys);
        session.setAttribute("expenseDataValues", expenseDataValues);
    }
    void getListOfBudgets(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        // Set up date values
        String startDateStr = "2024-03-01";
        String endDateStr = "2024-03-31";
        Date startDate = Date.valueOf(startDateStr);
        Date endDate = Date.valueOf(endDateStr);
        // Create list of budgets
        List<Budget> budgetList = budgetController.selectAllByMonth(startDate, endDate, userId);
        session.setAttribute("budgetList", budgetList);

    }
    void createListOfBackgroundImages(HttpServletRequest request){
        HttpSession session = request.getSession();

        List<String> backgroundImages = new ArrayList<>();
        backgroundImages.add("./images/accountBackground.png");
        backgroundImages.add("./images/accountBackground2.png");
        backgroundImages.add("./images/accountBackground3.png");
        backgroundImages.add("./images/accountBackground4.png");

        session.setAttribute("backgroundImages", backgroundImages);
    }
}


