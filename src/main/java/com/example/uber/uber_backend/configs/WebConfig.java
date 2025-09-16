package com.example.uber.uber_backend.configs;

import com.example.uber.uber_backend.interceptors.ApiLoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ApiLoggerInterceptor apiLoggerInterceptor;

    public void  addInterceptors(InterceptorRegistry interceptorRegistry){
        interceptorRegistry.addInterceptor(apiLoggerInterceptor);
    }
}
