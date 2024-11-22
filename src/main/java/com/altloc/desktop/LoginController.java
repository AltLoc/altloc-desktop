package com.altloc.desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label wrongLogin;

    private final AuthService authService = new AuthService();

    @FXML
    public void userLogin(ActionEvent event) {
        String userEmail = email.getText();
        String userPassword = password.getText();

        if (authService.login(userEmail, userPassword)) {
            wrongLogin.setText("");
            System.out.println("User successfully logged in!");
            // Действия после успешного входа, например, переход на новый экран.
        } else {
            wrongLogin.setText("Invalid email or password");
        }
    }
}
