package com.altloc.desktop.service;

import com.altloc.desktop.model.ApiResponse;
import com.altloc.desktop.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ApiResponseService {

    // Метод для десериализации ответа и получения объекта User
    public User getUserFromResponse(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Десериализуем JSON в ApiResponse
        ApiResponse apiResponse = objectMapper.readValue(responseBody, ApiResponse.class);
        // Возвращаем объект User из поля data
        return apiResponse.getData();
    }
}
