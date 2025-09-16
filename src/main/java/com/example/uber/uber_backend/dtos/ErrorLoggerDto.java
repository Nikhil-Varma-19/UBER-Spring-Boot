package com.example.uber.uber_backend.dtos;

import lombok.Data;

@Data
public class ErrorLoggerDto {
    private String body;
    private String method;
    private String url;
    private String message;
}
