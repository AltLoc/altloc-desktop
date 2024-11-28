package com.altloc.desktop.service;

import com.altloc.desktop.model.ApiResponse;
import com.altloc.desktop.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AuthService {

    private static final String BASE_URL = "http://localhost:4000"; // Замените на ваш URL
    private final OkHttpClient client;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper для работы с JSON
    private final InMemoryCookieJar cookieJar;

    // Конструктор с настройкой собственного CookieJar
    public AuthService() {
        cookieJar = new InMemoryCookieJar();

        client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
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
    public User getCurrentUser() {
        String url = BASE_URL + "/auth/me"; // URL для получения информации о текущем пользователе

        // Получаем доступные куки
        List<Cookie> cookies = cookieJar.loadForRequest(HttpUrl.get(BASE_URL));

        System.out.println("Cookies being sent:");
        cookies.forEach(cookie -> System.out.println(cookie.name() + ": " + cookie.value()));

        // Используем метод extractAccessToken для получения токена
        Optional<String> accessToken = extractAccessToken(cookies);

        if (accessToken.isEmpty()) {
            System.out.println("No access token found in cookies.");
            throw new RuntimeException("No access token found in cookies.");
        }

        // Создаем GET-запрос с Authorization header
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Authorization", "Bearer " + accessToken.get()) // Добавляем Bearer токен
                .header("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // Обработка успешного ответа
                String responseBody = response.body() != null ? response.body().string() : "";
                if (!responseBody.isEmpty()) {
                    return objectMapper.readValue(responseBody, User.class); // Преобразуем ответ в объект User
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

    // Метод для извлечения токена из куки
    private Optional<String> extractAccessToken(List<Cookie> cookies) {
        return cookies.stream()
                .filter(cookie -> "access_token".equals(cookie.name()))
                .map(Cookie::value)
                .findFirst();
    }
}
