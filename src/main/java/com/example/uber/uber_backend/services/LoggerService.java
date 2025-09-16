package com.example.uber.uber_backend.services;

import com.example.uber.uber_backend.dtos.ApiLoggerDto;
import com.example.uber.uber_backend.dtos.ErrorLoggerDto;

public interface LoggerService {
    void createApiLog(ApiLoggerDto apiLogger);

    void createErrorLog(ErrorLoggerDto errorLogger);
}
