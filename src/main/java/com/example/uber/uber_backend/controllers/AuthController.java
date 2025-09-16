package com.example.uber.uber_backend.controllers;


import com.example.uber.uber_backend.dtos.*;
import com.example.uber.uber_backend.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private  final AuthService authService;

    @PostMapping("/signup")
   ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        return  ResponseEntity.ok(authService.signup(signUpDto));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/onBoardDriver/{userId}")
    ResponseEntity<DriverDto> onBoardDriver(@PathVariable Long userId, @RequestBody DriverVehicleDto driverVehicleDto){
        return  ResponseEntity.ok(authService.newDriver(userId, driverVehicleDto.getVehicle()));
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse){
        LoginResponseDto loginResponseDto=authService.login(loginDto.getEmail(), loginDto.getPassword());
        Map<String,Object> response =new HashMap<>();
        response.put("roles",loginResponseDto.getRole());
        response.put("token",loginResponseDto.getToken());
        Cookie cookie=new Cookie("refreshToken",loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);
        return  ResponseEntity.ok(response);
    }
}
