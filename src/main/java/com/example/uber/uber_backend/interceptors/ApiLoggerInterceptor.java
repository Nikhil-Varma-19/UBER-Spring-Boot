package com.example.uber.uber_backend.interceptors;

import com.example.uber.uber_backend.dtos.ApiLoggerDto;
import com.example.uber.uber_backend.services.LoggerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;


@Component
public class ApiLoggerInterceptor implements HandlerInterceptor {
    @Autowired
    private LoggerService loggerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        ApiLoggerDto apiLoggerDto = new ApiLoggerDto();
        apiLoggerDto.setMethod(request.getMethod());
        apiLoggerDto.setUrl(request.getRequestURI());
        apiLoggerDto.setIpAddress(request.getRemoteAddr());
        loggerService.createApiLog(apiLoggerDto);
        return true;
    }
}
