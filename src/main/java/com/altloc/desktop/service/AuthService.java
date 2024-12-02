package com.altloc.desktop.service;

import com.altloc.desktop.model.UserResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * AuthService provides methods for authentication and fetching user
 * information.
 * This class implements a Singleton pattern to ensure only one instance is
 * created.
 */
public class AuthService {
    private static AuthService instance; // Singleton instance
    private final OkHttpClient client;
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON parser
    private final InMemoryCookieJar cookieJar;
    private static final String BASE_URL = "http://localhost:4000";

    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes the HTTP client with an in-memory cookie jar.
     */
    private AuthService() {
        cookieJar = new InMemoryCookieJar();
        client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
    }

    /**
     * Returns the Singleton instance of AuthService.
     *
     * @return The AuthService instance.
     */
    public static synchronized AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    /**
     * Performs user login with the provided credentials.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     * @return A map containing the response body as key-value pairs.
     * @throws IOException If an error occurs during the request or response
     *                     handling.
     */
    public Map<String, Object> login(String email, String password) throws IOException {
        String url = BASE_URL + "/auth/password/login";
        Map<String, String> credentials = Map.of(
                "email", email,
                "password", password);

        String json = objectMapper.writeValueAsString(credentials);

        RequestBody body = RequestBody.create(
                json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Login successful. Cookies should be set by the server.");

                String responseBody = response.body() != null ? response.body().string() : "";
                if (!responseBody.isEmpty()) {
                    return objectMapper.readValue(responseBody,
                            new TypeReference<Map<String, Object>>() {
                            });
                }

                return Map.of();
            } else {
                System.out.println("Login failed. Response code: " + response.code());
                throw new IOException("Failed to login, response code: " + response.code());
            }
        }
    }

    /**
     * Fetches the currently authenticated user's information.
     *
     * @return A UserResponse object containing the user's information.
     * @throws RuntimeException If no access token is found or an error occurs
     *                          during the request.
     */
    public UserResponse getCurrentUser() {
        String url = BASE_URL + "/auth/me";
        HttpUrl httpUrl = HttpUrl.parse(BASE_URL);

        List<Cookie> cookies = cookieJar.loadForRequest(httpUrl);

        System.out.println("Cookies being sent:");
        cookies.forEach(cookie -> System.out.println(cookie.name() + ": " + cookie.value()));

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

    /**
     * Logs out the current user by clearing the cookie jar.
     */
    public void logout() throws IOException {
        String url = BASE_URL + "/auth/password/logout";

        RequestBody body = RequestBody.create("{}", MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Logout successful.");
                cookieJar.clear();
            } else {
                System.out.println("Logout failed. Response code: " + response.code());
                throw new IOException("Failed to logout, response code: " + response.code());
            }
        }
    }
}
