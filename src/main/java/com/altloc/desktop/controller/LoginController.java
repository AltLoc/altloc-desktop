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

public class LoginController {

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label wrongLogin;

    @FXML
    private Button loginButton;

    private final AuthService authService = AuthService.getInstance();

    @FXML
    public void userLogin(ActionEvent event) {
        String userEmail = email.getText().trim();
        String userPassword = password.getText().trim();

        // Блокируем кнопку входа, чтобы предотвратить повторное нажатие
        loginButton.setDisable(true);
        wrongLogin.setText(""); // Сброс текста ошибки

        // Асинхронный запрос для входа
        CompletableFuture.runAsync(() -> {
            try {
                // Попытка входа
                try {
                    authService.login(userEmail, userPassword);
                } catch (IOException e) {
                    Platform.runLater(() -> wrongLogin.setText("Login failed: " + e.getMessage()));
                    return;
                }

                // Если вход успешен, получаем текущего пользователя
                UserResponse user = authService.getCurrentUser();

                // Если успешно, выполняем действия в JavaFX-потоке
                Platform.runLater(() -> {
                    if (user != null) {
                        wrongLogin.setText(""); // Сброс текста ошибки при успешном входе
                        System.out.println("User successfully logged in!");

                        // Переход на экран панели пользователя
                        try {
                            App.setRoot("UserDashboard"); // Переход к панели пользователя
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        wrongLogin.setText("Failed to fetch user data.");
                    }
                });
            } catch (RuntimeException ex) {
                // Если ошибка, отображаем её в интерфейсе
                Platform.runLater(() -> wrongLogin.setText(ex.getMessage()));
            } finally {
                // Снимаем блокировку с кнопки входа
                Platform.runLater(() -> loginButton.setDisable(false));
            }
        });
    }
}
