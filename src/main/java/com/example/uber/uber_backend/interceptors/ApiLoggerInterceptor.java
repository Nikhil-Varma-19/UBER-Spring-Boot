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
        if(request instanceof ContentCachingRequestWrapper wrapper){
            // ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
            byte[] content= wrapper.getContentAsByteArray();
            String body=new String(content, StandardCharsets.UTF_8);
            System.out.println("bodyyyy"+body);
            apiLoggerDto.setBody(body);
        }else {
            apiLoggerDto.setBody(null);
        }
        loggerService.createApiLog(apiLoggerDto);
        return true;
    }
}
