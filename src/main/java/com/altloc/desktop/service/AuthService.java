package com.altloc.desktop.service;

import com.altloc.desktop.model.UserResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AuthService {
    private static AuthService instance; // Singleton instance
    private final OkHttpClient client;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper для работы с JSON
    private final InMemoryCookieJar cookieJar;
    private static final String BASE_URL = "http://localhost:4000";

    // Приватный конструктор для Singleton
    private AuthService() {
        cookieJar = new InMemoryCookieJar();
        client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
    }

    // Метод для получения Singleton instance
    public static synchronized AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    // Метод для выполнения логина
    public Map<String, Object> login(String email, String password) throws IOException {
        String url = BASE_URL + "/auth/password/login"; // URL для логина
        Map<String, String> credentials = Map.of(
                "email", email,
                "password", password);

        // Преобразуем данные в JSON
        String json = objectMapper.writeValueAsString(credentials);

        // Создаем тело запроса
        RequestBody body = RequestBody.create(
                json, MediaType.parse("application/json"));

        // Создаем POST-запрос
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // Отправляем запрос и обрабатываем ответ
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Login successful. Cookies should be set by the server.");

                // Читаем тело ответа
                String responseBody = response.body() != null ? response.body().string() : "";
                if (!responseBody.isEmpty()) {
                    Map<String, Object> responseMap = objectMapper.readValue(responseBody,
                            new TypeReference<Map<String, Object>>() {
                            });
                    return responseMap; // Возвращаем тело ответа
                }

                return Map.of(); // Если тело пустое, возвращаем пустую карту
            } else {
                System.out.println("Login failed. Response code: " + response.code());
                throw new IOException("Failed to login, response code: " + response.code());
            }
        }
    }

    // Метод для получения текущего пользователя
    public UserResponse getCurrentUser() {
        String url = BASE_URL + "/auth/me"; // URL для получения информации о текущем пользователе
        HttpUrl httpUrl = HttpUrl.parse(BASE_URL);

        // Получаем доступные куки с использованием InMemoryCookieJar
        List<Cookie> cookies = cookieJar.loadForRequest(httpUrl);

        System.out.println("Cookies being sent:");
        cookies.forEach(cookie -> System.out.println(cookie.name() + ": " + cookie.value()));

        // Пытаемся извлечь access token из cookies
        String accessToken = null;
        for (Cookie cookie : cookies) {
            if ("access_token".equals(cookie.name())) {
                accessToken = cookie.value();
                break;
            }
        }

        if (accessToken == null) {
            System.out.println("No access token found in cookies.");
            throw new RuntimeException("No access token found in cookies.");
        }

        System.out.println("Access Token: " + accessToken);

        // Создаем GET-запрос с Authorization header
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body() != null ? response.body().string() : "";
                if (!responseBody.isEmpty()) {
                    return objectMapper.readValue(responseBody, UserResponse.class);
                } else {
                    throw new RuntimeException("Empty response body");
                }
            } else {
                throw new IOException("Failed to fetch user info. Response code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching user info", e);
        }
    }
}
