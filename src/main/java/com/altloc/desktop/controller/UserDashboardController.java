package com.altloc.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.altloc.desktop.model.UserResponse;
import com.altloc.desktop.service.AuthService;

import javafx.application.Platform;

public class UserDashboardController {

    @FXML
    private Label username;

    @FXML
    private Label level;

    @FXML
    private Label email;

    @FXML
    private Label balance;

    @FXML
    private Label createdAt;

    private final AuthService authService = AuthService.getInstance();

    // В контроллере:
    public void initialize() {
        // Асинхронно получаем данные о текущем пользователе
        new Thread(() -> {
            try {
                // Получаем данные о текущем пользователе
                UserResponse user = authService.getCurrentUser();

                // Проверяем, что данные о пользователе существуют
                if (user != null) {
                    // Обновляем UI компоненты в главном потоке
                    Platform.runLater(() -> {
                        username.setText(user.getData().getUsername());
                        level.setText(String.valueOf(user.getData().getLevel()));
                        email.setText(user.getData().getEmail());
                        balance.setText(String.valueOf(user.getData().getCurrency()));
                        createdAt.setText(String.valueOf(user.getData().getCreatedAt()));

                    });
                } else {
                    // Если данных нет, показываем дефолтные значения
                    Platform.runLater(() -> {
                        username.setText("Unknown");
                        level.setText("0");
                        balance.setText("0");
                    });
                }

                System.out.println("Is Admin: " + user.getData().isAdmin());
            } catch (Exception e) {
                // Обработка ошибок и отображение дефолтных значений
                Platform.runLater(() -> {
                    username.setText("Error");
                    level.setText("0");
                    balance.setText("0");

                });
                e.printStackTrace();
            }
        }).start();
    }

}
