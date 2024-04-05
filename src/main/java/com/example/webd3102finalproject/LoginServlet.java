package com.example.webd3102finalproject;

import com.example.webd3102finalproject.controller.UserController;
import com.example.webd3102finalproject.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;


@WebServlet(urlPatterns="/login", loadOnStartup = 1, name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    private UserController userController;
    public void init() {
        userController = new UserController();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String action = request.getServletPath();
        RequestDispatcher dispatcher;
        try{
            switch(action){
                case "/login":
                    dispatcher = request.getRequestDispatcher("/login.jsp");
                    dispatcher.forward(request, response);
                    break;
                case "/logout":
                    logout(request, response);
                    break;
            }
        } catch (IOException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getServletPath();
        if (action.equals("/login/auth")) {
            login(request, response);
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        // Get email and password from form, create new user object
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user;
        try{
            user = userController.select(email);
            // Check password against hashed password using BCrypt, if all good then set session attribute and redirect
            if(user != null && BCrypt.checkpw(password, user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                String errorMessage = "Invalid. Please try again.";
                response.sendRedirect(request.getContextPath() + "/login?error=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        // remove all session attributes
        session.removeAttribute("user");
        session.removeAttribute("accountList");
        session.removeAttribute("totalWealth");
        session.removeAttribute("totalExpenses");
        session.removeAttribute("totalIncome");
        session.removeAttribute("startDate");
        session.removeAttribute("endDate");
        session.removeAttribute("alreadySet");
        session.removeAttribute("account");
        session.removeAttribute("budgetAdditionError");
        session.removeAttribute("transactionError");
        session.removeAttribute("incomeTransactions");
        session.removeAttribute("expenseTransactions");
        session.removeAttribute("transactionList");
        session.removeAttribute("expenseDataKeys");
        session.removeAttribute("expenseDataValues");
        session.removeAttribute("budgetList");
        session.removeAttribute("backgroundImages");
        String successMessage = "Successfully logged out.";
        response.sendRedirect(request.getContextPath() + "/home?logoutSuccess=" + URLEncoder.encode(successMessage, StandardCharsets.UTF_8));
    }
}