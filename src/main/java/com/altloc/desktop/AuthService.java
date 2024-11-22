package com.altloc.desktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static final String BASE_URL = "http://localhost:4000/api";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper

    public boolean login(String email, String password) {
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
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    System.out.println("Login successful!");
                    return true;
                } else {
                    System.out.println("Login failed: " + response.code());
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
