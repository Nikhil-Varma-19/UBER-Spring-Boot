package com.example.uber.uber_backend.configs;

import com.example.uber.uber_backend.interceptors.ApiLoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private ApiLoggerInterceptor apiLoggerInterceptor;

    @Value("${dir.getFiles}")
    private String getUploadsPath;

    @Value("${dir.uploadFile}")
    private String uploadsPath;


    @Override
    public void  addInterceptors(InterceptorRegistry interceptorRegistry){
        interceptorRegistry.addInterceptor(apiLoggerInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        String fileHandler=getUploadsPath+"/**";
        String[] arrayFile=uploadsPath.split("/");
        registry.addResourceHandler(fileHandler)
                .addResourceLocations("file:"+arrayFile[1]+"/");
    }

}
