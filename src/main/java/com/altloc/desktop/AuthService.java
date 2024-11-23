package com.altloc.desktop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static final String BASE_URL = "http://localhost:4000";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper

    public Map<String, Object> login(String email, String password) {
        String url = BASE_URL + "/auth/password/login";

        // Создание карты данных для JSON
        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);

        try {
            // Сериализация карты в JSON строку
            String json = objectMapper.writeValueAsString(data);

            // Создание тела запроса с JSON
            RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .header("Accept", "application/json") // Указание заголовков
                    .header("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body() != null ? response.body().string() : "";

                if (response.isSuccessful()) {
                    if (!responseBody.isEmpty()) {
                        // Безопасное преобразование JSON в Map<String, Object>
                        Map<String, Object> responseData = objectMapper.readValue(
                                responseBody, new TypeReference<Map<String, Object>>() {
                                });
                        System.out.println("Login successful! Response: " + responseData);
                        return responseData; // Возвращаем данные
                    } else {
                        System.out.println("Login successful! No additional data returned.");
                        return new HashMap<>(); // Возвращаем пустую Map
                    }
                } else {
                    // Если ошибка, читаем тело и пытаемся извлечь сообщение
                    String errorMessage = "Unknown error";
                    if (!responseBody.isEmpty()) {
                        try {
                            // Безопасное преобразование JSON в Map<String, Object>
                            Map<String, Object> errorData = objectMapper.readValue(
                                    responseBody, new TypeReference<Map<String, Object>>() {
                                    });
                            if (errorData.containsKey("errors")) {
                                errorMessage = ((Map<?, ?>) ((java.util.List<?>) errorData.get("errors")).get(0))
                                        .get("message")
                                        .toString();
                            }
                        } catch (Exception ex) {
                            errorMessage = "Failed to parse error response: " + responseBody;
                        }
                    } else {
                        errorMessage = "Empty error response received.";
                    }
                    System.err.println("Login failed: " + response.code() + ", Error: " + errorMessage);
                    throw new RuntimeException(errorMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to server: " + e.getMessage());
        }
    }
}
