package com.example.uber.uber_backend.services;

import com.example.uber.uber_backend.dtos.DriverDto;
import com.example.uber.uber_backend.dtos.LoginResponseDto;
import com.example.uber.uber_backend.dtos.SignUpDto;
import com.example.uber.uber_backend.dtos.UserDto;
import jakarta.servlet.http.Cookie;

public interface AuthService {

    LoginResponseDto login(String email, String password);

    UserDto signup(SignUpDto signdto);

    DriverDto newDriver(Long userId,String vehicleId);

    String refreshToken(Cookie[] cookies);
}
