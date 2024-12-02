package com.altloc.desktop.service;

import com.altloc.desktop.model.UserResponse;
import com.altloc.desktop.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Service class for processing API responses.
 * Provides methods for deserializing response bodies into model objects.
 */
public class ApiResponseService {

    /**
     * Deserializes the API response body and extracts the User object.
     *
     * @param responseBody The JSON response body as a string.
     * @return The User object extracted from the response.
     * @throws IOException If an error occurs during deserialization.
     */
    public User getUserFromResponse(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Deserialize JSON into UserResponse
        UserResponse apiResponse = objectMapper.readValue(responseBody, UserResponse.class);

        System.out.println("Response data: " + apiResponse.getData());
        // Return the User object from the "data" field
        return apiResponse.getData();
    }
}
