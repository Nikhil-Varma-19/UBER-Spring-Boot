package com.example.uber.uber_backend.dtos;

import lombok.Data;

@Data
public class ApiLoggerDto {
    private String url;
    private String method;
    private String body;
    private String userId;
    private String ipAddress;
}
