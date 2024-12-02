package com.altloc.desktop.controller;

import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
// import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

import com.altloc.desktop.Utils;
import com.altloc.desktop.model.UserResponse;
import com.altloc.desktop.service.AuthService;
import com.altloc.desktop.App;

import javafx.application.Platform;
import javafx.event.ActionEvent;

/**
 * Controller for managing the User Dashboard screen in the JavaFX application.
 * This class retrieves and displays user data such as username, level, email,
 * balance, and account creation date.
 * It interacts with the AuthService to fetch current user data asynchronously.
 */
public class UserDashboardController {

    @FXML
    private Label username; // Label for displaying the user's username

    @FXML
    private Label userLevel; // Label for displaying the user's level

    @FXML
    private Label email; // Label for displaying the user's email

    @FXML
    private Label userCurrency; // Label for displaying the user's balance (currency)

    @FXML
    private Label createdAt; // Label for displaying the account creation date

    @FXML
    private ImageView avatar; // Image view for displaying the user's avatar

    @FXML
    private Button logout; // Button to trigger the logout process

    private final AuthService authService = AuthService.getInstance(); // Instance of AuthService to get user data

    /**
     * Initializes the User Dashboard by asynchronously fetching user data.
     * This method retrieves the current user's information from the AuthService and
     * updates the UI with the user details.
     * The UI update occurs on the JavaFX application thread using
     * Platform.runLater().
     * If an error occurs or no user data is found, default values are displayed on
     * the UI.
     */
    public void initialize() {
        // Asynchronously fetch user data
        new Thread(() -> {
            try {
                // Fetch current user data
                UserResponse user = authService.getCurrentUser();

                // If user data is available, update UI components
                if (user != null) {
                    // Update UI labels with user data on the JavaFX application thread
                    Platform.runLater(() -> {
                        username.setText(user.getData().getUsername());
                        userLevel.setText(String.valueOf(user.getData().getLevel()));
                        email.setText(user.getData().getEmail());
                        userCurrency.setText(String.valueOf(user.getData().getCurrency()));
                        try {
                            avatar.setImage(new Image(Utils.getCDNImageURL(user.getData().getAvatarKey())));
                        } catch (URISyntaxException e) {
                            e.printStackTrace(); // Log the exception
                            avatar.setImage(null); // Optionally, set a default image if URL is invalid
                        }
                    });
                } else {
                    // If user data is not found, show default values
                    Platform.runLater(() -> {
                        username.setText("Unknown");
                        userLevel.setText("0");
                        userCurrency.setText("0");
                        email.setText("Unknown");
                    });
                }

                // Log whether the user is an admin
                System.out.println("Is Admin: " + user.getData().isAdmin());
            } catch (Exception e) {
                // Handle any errors and update UI with default values
                Platform.runLater(() -> {
                    username.setText("Error");
                    userLevel.setText("0");
                    userCurrency.setText("0");
                    email.setText("Error");
                });
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Handles the logout button click event.
     * Calls the logout method from AuthService and closes the current screen
     * or redirects to the login screen.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void handleLogout(ActionEvent event) {
        CompletableFuture.runAsync(() -> {
            try {
                authService.logout();
                System.out.println("Logout successful.");

                Platform.runLater(() -> {
                    try {
                        App.setRoot("userLogin");
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.err.println("Failed to load login screen: " + e.getMessage());
                    }
                });
            } catch (Exception e) {
                System.err.println("Logout failed: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

}
