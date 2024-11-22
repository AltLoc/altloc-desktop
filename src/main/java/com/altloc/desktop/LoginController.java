package com.altloc.desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label wrongLogin;

    public LoginController() {
        // Конструктор по умолчанию
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("userLogin");
    }

    @FXML
    public void userLogin(ActionEvent event) throws IOException {
        System.out.println("User login");
        if (checkLogin()) {
            System.out.println("Login successful");
            wrongLogin.setText("");
        } else {
            System.out.println("Login failed");
            wrongLogin.setText("Invalid email or password");
        }
    }

    private boolean checkLogin() {
        String enteredEmail = email.getText();
        String enteredPassword = password.getText();

        if ("admin@example.com".equals(enteredEmail) && "password123".equals(enteredPassword)) {
            return true;
        }
        return false;
    }
}
