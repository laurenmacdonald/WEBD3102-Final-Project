package com.example.webd3102finalproject;
import com.example.webd3102finalproject.controller.UserController;
import com.example.webd3102finalproject.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet(urlPatterns="/signup", loadOnStartup = 1, name = "SignUpServlet")
public class SignUpServlet extends HttpServlet {

    private UserController userController;

    public void init() {
        userController = new UserController();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String action = request.getServletPath();
        RequestDispatcher dispatcher;
        try{
            if (action.equals("/signup")) {
                dispatcher = request.getRequestDispatcher("/signup.jsp");
                dispatcher.forward(request, response);
            }
        } catch (IOException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getServletPath();
        if (action.equals("/signup/create-account")) {
            createNewAccount(request, response);
        }
    }

    public void createNewAccount(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        // Hash the password using BCrypt before sending it to db.
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        User newUser = new User(firstName, lastName, email, passwordHash);
        try{
            // If the sign-up is successful, redirect to log in with success message
            if(userController.create(newUser) == 1){
                String successMessage = "Sign up successful! Login to continue.";
                response.sendRedirect(request.getContextPath() + "/login?signupSuccess=" + URLEncoder.encode(successMessage, StandardCharsets.UTF_8));
                // if unsuccessful, display error message on sign up page.
            } else {
                String errorMessage = "Error with sign up. Please try again.";
                response.sendRedirect(request.getContextPath() + "/signup?signupSuccess=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
