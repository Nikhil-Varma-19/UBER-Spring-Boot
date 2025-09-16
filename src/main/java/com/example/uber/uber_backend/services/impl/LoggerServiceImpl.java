package com.example.uber.uber_backend.services.impl;

import com.example.uber.uber_backend.dtos.ApiLoggerDto;
import com.example.uber.uber_backend.dtos.ErrorLoggerDto;
import com.example.uber.uber_backend.entities.ApiLogger;
import com.example.uber.uber_backend.entities.ErrorLogger;
import com.example.uber.uber_backend.repostities.ApiLoggerRepository;
import com.example.uber.uber_backend.repostities.ErrorLoggerRepository;
import com.example.uber.uber_backend.services.LoggerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class LoggerServiceImpl implements LoggerService {
    @Autowired
    private ApiLoggerRepository apiLoggerRepository;
    @Autowired
    private ErrorLoggerRepository errorLoggerRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createApiLog(ApiLoggerDto apiLogger) {
        ApiLogger createApiLogger=modelMapper.map(apiLogger,ApiLogger.class);
        apiLoggerRepository.save(createApiLogger);
    }

    @Override
    public void createErrorLog(ErrorLoggerDto errorLogger) {
        ErrorLogger createErrorLogger=modelMapper.map(errorLogger,ErrorLogger.class);
        errorLoggerRepository.save(createErrorLogger);
    }
}
