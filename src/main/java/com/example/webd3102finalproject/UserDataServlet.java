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

@WebServlet(urlPatterns = "/", loadOnStartup = 1, name = "UserDataServlet")
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
        String action = request.getServletPath();
        String monthParam = request.getParameter("month");
        switch (action) {
            // Always load user data and determine the date
            case "/dashboard":
            case "/budgets":
            case "/transactions":
                if(session.getAttribute("user") != null){
                    // if the month parameter is in the url get the month and load the user data
                    if (monthParam != null && !monthParam.isEmpty()) {
                        getMonth(request, response);
                        loadUserData(request, response, action);
                    } else {
                        // if month is already set, just load user data
                        if (session.getAttribute("alreadySet") != null) {
                            loadUserData(request, response, action);
                        } else {
                            // if there is no month parameter and not already set, set the date to April and load user data
                            String startDateStr = "2024-04-01";
                            String endDateStr = "2024-04-30";
                            Date startDate = Date.valueOf(startDateStr);
                            Date endDate = Date.valueOf(endDateStr);
                            session.setAttribute("startDate", startDate);
                            session.setAttribute("endDate", endDate);
                            loadUserData(request, response, action);
                        }
                    }
                    // if user session attribute is not set (user not logged in) redirect to home
                } else {
                    response.sendRedirect(request.getContextPath() + "/home");
                }
                break;
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/add-account":
                addAccount(request, response);
                break;
            case "/add-budget":
                addBudget(request, response);
                break;
            case "/add-transaction":
                try {
                    addTransaction(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }
    public void loadUserData(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        try {
            getTotalWealth(request, response);
            getListOfAllTransactions(request);
            getTransactionsByType(request);
            getListOfBudgets(request);
            getArrayOfExpenseSummaryData(request);
            createListOfBackgroundImages(request);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        // action will be either /dashboard, /transactions, or /budgets. Will forward request accordingly.
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(action);
        stringBuilder.append(".jsp");
        dispatcher = request.getRequestDispatcher(stringBuilder.toString());
        dispatcher.forward(request, response);
    }

    /**
     * Determine the date range for the user data based on request parameter int value.
     * Set the start date and end date, and store it in a session attribute.
     * Set already set boolean to indicate the date is already set
     * @param request request from doget
     * @param response response from doget
     */
    public void getMonth(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String startDateStr = "";
        String endDateStr = "";
        int month = Integer.parseInt(request.getParameter("month"));
        switch (month) {
            case 1:
                startDateStr = "2024-01-01";
                endDateStr = "2024-01-31";
                break;
            case 2:
                startDateStr = "2024-02-01";
                endDateStr = "2024-02-29";
                break;
            case 3:
                startDateStr = "2024-03-01";
                endDateStr = "2024-03-31";
                break;
            case 4:
                startDateStr = "2024-04-01";
                endDateStr = "2024-04-30";
                break;
            case 5:
                startDateStr = "2024-05-01";
                endDateStr = "2024-05-31";
                break;
            case 6:
                startDateStr = "2024-06-01";
                endDateStr = "2024-06-30";
                break;
            case 7:
                startDateStr = "2024-07-01";
                endDateStr = "2024-07-31";
                break;
            case 8:
                startDateStr = "2024-08-01";
                endDateStr = "2024-08-31";
                break;
            case 9:
                startDateStr = "2024-09-01";
                endDateStr = "2024-09-30";
                break;
            case 10:
                startDateStr = "2024-10-01";
                endDateStr = "2024-10-31";
                break;
            case 11:
                startDateStr = "2024-11-01";
                endDateStr = "2024-11-30";
                break;
            case 12:
                startDateStr = "2024-12-01";
                endDateStr = "2024-12-31";
                break;
        }
        Date startDate = Date.valueOf(startDateStr);
        Date endDate = Date.valueOf(endDateStr);
        session.setAttribute("startDate", startDate);
        session.setAttribute("endDate", endDate);
        session.setAttribute("alreadySet", true);
    }

    /**
     * Gets the userID from session attribute user object and applicable account information from form
     * post request. Creates new account object and supplies it to accountController's create method,
     * adding a new account to the database associated with the user.
     * @param request dopost request
     * @param response dopost response
     * @throws IOException will throw this exception
     */
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

    /**
     * Gets the userID from session attribute user object and applicable budget information from form
     * post request. Creates new budget object and supplies it to budgetController's create method,
     * adding a new budget to the database associated with the user.
     * Redirects back to the url from which the request was sent (/budgets or /dashboard).
     * @param request dopost request
     * @param response dopost response
     * @throws IOException
     */
    public void addBudget(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        int categoryId = Integer.parseInt(request.getParameter("category"));
        double goalAmount = Double.parseDouble(request.getParameter("goalAmount"));
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        Budget budget = new Budget(userId, categoryId, goalAmount, startDate, endDate);

        try {
            if (budgetController.create(budget) == 1) {
                String[] url = request.getHeader("referer").split("/");
                response.sendRedirect(url[url.length-1]);
            } else {
                String budgetErrorMessage = "Error adding budget.";
                session.setAttribute("budgetAdditionError", budgetErrorMessage);
                String[] url = request.getHeader("referer").split("/");
                response.sendRedirect(url[url.length-1]+"/?budgetError="+URLEncoder.encode(budgetErrorMessage, StandardCharsets.UTF_8));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * Gets the applicable transaction information from form post request.
     * Creates new transaction object and supplies it to transactionController's create method,
     * adding a new transaction to the database associated with the user.
     * Redirects back to the url from which the request was sent (/transactions or /dashboard).
     * @param request dopost request
     * @param response dopost response
     * @throws IOException
     */
    public void addTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession();
        // Get information from form submission (post parameters)
        int accountID = Integer.parseInt(request.getParameter("accountID"));
        int categoryId = Integer.parseInt(request.getParameter("category"));
        // Determine the transactionType string based on ID
        String transactionType;
        if (categoryId == 15 || categoryId == 16 || categoryId == 17) {
            transactionType = "Income";
        } else {
            transactionType = "Expense";
        }
        double amount = Double.parseDouble(request.getParameter("amount"));
        Date date = Date.valueOf(request.getParameter("date"));
        String budgetLink = request.getParameter("budgetLink");
        int budgetID = 0;
        // If the user indicates there is a budget to link, get the budgetID by supplying the associated categoryID.
        if (budgetLink.equals("yes")) {
            budgetID = budgetController.selectBudgetID(categoryId);
        }
        // Create new transaction object and supply it to the DB via transactionController's create method.
        // Redirect to the page from which the request was sent (/transactions or /dashboard).
        Transaction transaction = new Transaction(accountID, categoryId, transactionType, amount, budgetID, date);
        try {
            if (transactionController.create(transaction) == 1) {
                String[] url = request.getHeader("referer").split("/");
                response.sendRedirect(url[url.length-1]);
            } else {
                String transactionErrorMessage = "Error adding transaction.";
                session.setAttribute("transactionError", transactionErrorMessage);
                String[] url = request.getHeader("referer").split("/");
                response.sendRedirect(url[url.length-1]+"/?transactionError="+URLEncoder.encode(transactionErrorMessage, StandardCharsets.UTF_8));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Get the total wealth of the user (all accounts balances added together), and the account list for the user.
     * Stores results in session attributes.
     * @param request doget request
     * @param response doget response
     * @throws SQLException
     * @throws IOException
     */
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

    /**
     * Retrieves lists of transactions (income and expense) and stores in session attributes.
     * @param request
     * @throws SQLException
     */
    private void getTransactionsByType(HttpServletRequest request) throws SQLException {
        // Get user ID from the session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        // Set up date values
        Date startDate = (Date) session.getAttribute("startDate");
        Date endDate = (Date) session.getAttribute("endDate");
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

    /**
     * Gets list of all transactions and stores in session attributes.
     * @param request
     * @throws SQLException
     */
    void getListOfAllTransactions(HttpServletRequest request) throws SQLException {
        // Get user ID from the session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        // Set up date values
        Date startDate = (Date) session.getAttribute("startDate");
        Date endDate = (Date) session.getAttribute("endDate");
        // Create list of transactions
        List<Transaction> transactionList = transactionController.selectAll(startDate, endDate, userId);
        session.setAttribute("transactionList", transactionList);
    }

    /**
     * Gets HashMap (CategoryName, amount) of expense data, saves keys and values in separate arrays which are saved in
     * session attributes to be used in Plot.ly graph on the dashboard.
     * @param request
     * @throws SQLException
     */
    void getArrayOfExpenseSummaryData(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        // Set up date values
        Date startDate = (Date) session.getAttribute("startDate");
        Date endDate = (Date) session.getAttribute("endDate");
        HashMap<String, Double> expenseData = transactionController.expenseData("Expense", userId, startDate, endDate);
        String[] expenseDataKeys = expenseData.keySet().toArray(new String[0]);
        Double[] expenseDataValues = expenseData.values().toArray(new Double[0]);
        session.setAttribute("expenseDataKeys", expenseDataKeys);
        session.setAttribute("expenseDataValues", expenseDataValues);
    }

    /**
     * Gets the list of budgets from the database for the user within start and end dates.
     * @param request
     * @throws SQLException
     */
    void getListOfBudgets(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Date startDate = (Date) session.getAttribute("startDate");
        Date endDate = (Date) session.getAttribute("endDate");
        int userId = user.getUserId();
        List<Budget> budgetList = budgetController.selectAllByMonth(userId, startDate, endDate);
        session.setAttribute("budgetList", budgetList);
    }

    /**
     * Get list of background image paths to be used as background of account card on dashboard.
     * @param request
     */
    void createListOfBackgroundImages(HttpServletRequest request) {
        HttpSession session = request.getSession();

        List<String> backgroundImages = new ArrayList<>();
        backgroundImages.add("./images/accountBackground.png");
        backgroundImages.add("./images/accountBackground2.png");
        backgroundImages.add("./images/accountBackground3.png");
        backgroundImages.add("./images/accountBackground4.png");

        session.setAttribute("backgroundImages", backgroundImages);
    }
}


