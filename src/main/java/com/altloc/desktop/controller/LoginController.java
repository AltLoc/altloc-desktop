package com.altloc.desktop.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import com.altloc.desktop.App;
import com.altloc.desktop.model.UserResponse;
import com.altloc.desktop.service.AuthService;

/**
 * Controller for handling user login interactions in the JavaFX application.
 * This class communicates with the AuthService to perform the login process and
 * manage user authentication.
 */
public class LoginController {

    @FXML
    private TextField email; // Email input field for the user

    @FXML
    private PasswordField password; // Password input field for the user

    @FXML
    private Label wrongLogin; // Label to display login error messages

    @FXML
    private Button loginButton; // Button to trigger the login process

    private final AuthService authService = AuthService.getInstance(); // Instance of AuthService to handle
                                                                       // authentication

    /**
     * Handles user login action when the login button is clicked.
     * This method asynchronously attempts to log in the user and handles both
     * success and failure scenarios.
     *
     * @param event The event triggered by clicking the login button.
     */
    @FXML
    public void userLogin(ActionEvent event) {
        String userEmail = email.getText().trim(); // Get the email input
        String userPassword = password.getText().trim(); // Get the password input

        // Disable login button to prevent repeated clicks during login process
        loginButton.setDisable(true);
        wrongLogin.setText(""); // Reset error label

        // Asynchronous login attempt using CompletableFuture
        CompletableFuture.runAsync(() -> {
            try {
                // Try to log in the user
                try {
                    authService.login(userEmail, userPassword);
                } catch (IOException e) {
                    // If login fails, display error message
                    Platform.runLater(() -> wrongLogin.setText("Login failed: " + e.getMessage()));
                    return;
                }

                // If login is successful, fetch the current user
                UserResponse user = authService.getCurrentUser();

                // Handle user data in the JavaFX UI thread
                Platform.runLater(() -> {
                    if (user != null) {
                        wrongLogin.setText(""); // Clear error label on successful login
                        System.out.println("User successfully logged in!");

                        // Transition to the user dashboard screen
                        try {
                            App.setRoot("dashboard"); // Change to user dashboard view
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Display an error if user data cannot be fetched
                        wrongLogin.setText("Failed to fetch user data.");
                    }
                });
            } catch (RuntimeException ex) {
                // Display runtime errors in the UI thread
                Platform.runLater(() -> wrongLogin.setText(ex.getMessage()));
            } finally {
                // Re-enable the login button after the process is complete
                Platform.runLater(() -> loginButton.setDisable(false));
            }
        });
    }
}
