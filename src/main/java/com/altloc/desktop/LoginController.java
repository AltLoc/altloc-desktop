package com.altloc.desktop;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.concurrent.CompletableFuture;

public class LoginController {
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label wrongLogin;

    @FXML
    private Button loginButton;

    private final AuthService authService = new AuthService();

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
                authService.login(userEmail, userPassword);

                // Если успешно, выполняем действия в JavaFX-потоке
                Platform.runLater(() -> {
                    wrongLogin.setText("");
                    System.out.println("User successfully logged in!");
                    // Действия после успешного входа, например, переход на новый экран
                    // loadNewScreen(); // Пример функции для загрузки нового экрана
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
