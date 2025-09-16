package com.example.uber.uber_backend.advices;


import lombok.Data;

@Data
public class ApiResponse<T> {
    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(ApiError error){
        this.error=error;
    }

    private T data;
    private ApiError error;

}
