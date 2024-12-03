package com.altloc.desktop.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
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

/**
 * Controller for managing the User Dashboard screen in the JavaFX application.
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

    @FXML
    private Button play; // Button to trigger the play process

    private final AuthService authService = AuthService.getInstance(); // Instance of AuthService to get user data

    /**
     * Initializes the User Dashboard by asynchronously fetching user data.
     */
    public void initialize() {
        new Thread(() -> {
            try {
                UserResponse user = authService.getCurrentUser();
                if (user != null) {
                    Platform.runLater(() -> {
                        username.setText(user.getData().getUsername());
                        userLevel.setText(String.valueOf(user.getData().getLevel()));
                        email.setText(user.getData().getEmail());
                        userCurrency.setText(String.valueOf(user.getData().getCurrency()));
                        try {
                            avatar.setImage(new Image(Utils.getCDNImageURL(user.getData().getAvatarKey())));
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                            avatar.setImage(null); // Set default image in case of error
                        }
                    });
                } else {
                    Platform.runLater(() -> {
                        username.setText("Unknown");
                        userLevel.setText("0");
                        userCurrency.setText("0");
                        email.setText("Unknown");
                    });
                }
                System.out.println("Is Admin: " + user.getData().isAdmin());
            } catch (Exception e) {
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
     */
    @FXML
    private void handleLogout() {
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

    /**
     * Handles the play button click event.
     * Switches the user to the Playground scene.
     */
    @FXML
    private void handlePlayButtonAction() {
        try {

            App.setRoot("playground");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load the Playground scene: " + e.getMessage());
        }
    }
}
