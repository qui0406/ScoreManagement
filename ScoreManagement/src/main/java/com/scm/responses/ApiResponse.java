package com.scm.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Data
public class ApiResponse<T> {
    private String status;
    private String message;
    private Map<String, Object> data;

    public static ApiResponse success(String message) {
        ApiResponse response = new ApiResponse();
        response.setStatus("success");
        response.setMessage(message);
        return response;
    }

    public static ApiResponse error(String message, BindingResult bindingResult) {
        ApiResponse response = new ApiResponse();
        response.setStatus("error");
        response.setMessage(message);

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        response.setData(Map.of("errors", errors));
        return response;
    }
}