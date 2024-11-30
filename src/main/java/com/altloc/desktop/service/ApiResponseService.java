package com.altloc.desktop.service;

import com.altloc.desktop.model.UserResponse;
import com.altloc.desktop.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ApiResponseService {

    // Метод для десериализации ответа и получения объекта User
    public User getUserFromResponse(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Десериализуем JSON в ApiResponse
        UserResponse apiResponse = objectMapper.readValue(responseBody, UserResponse.class);

        System.out.println("Response data: " + apiResponse.getData());
        // Возвращаем объект User из поля data

        return apiResponse.getData();
    }
}
