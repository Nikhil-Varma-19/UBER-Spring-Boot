package com.example.uber.uber_backend.services;

import com.example.uber.uber_backend.dtos.DriverDto;
import com.example.uber.uber_backend.dtos.LoginResponseDto;
import com.example.uber.uber_backend.dtos.SignUpDto;
import com.example.uber.uber_backend.dtos.UserDto;
import jakarta.servlet.http.Cookie;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {

    LoginResponseDto login(String email, String password);

    UserDto signup(SignUpDto signdto);

    DriverDto newDriver(Long userId, String vehicleId, MultipartFile file);

    String refreshToken(Cookie[] cookies);

    void forgotPassword(String email);
}
