package com.example.uber.uber_backend.controllers;


import com.example.uber.uber_backend.advices.ApiResponse;
import com.example.uber.uber_backend.dtos.*;
import com.example.uber.uber_backend.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

//
//    @PostMapping("/onBoardDriver/{userId}")
//    ResponseEntity<DriverDto> onBoardDriver(@PathVariable Long userId, @RequestParam(value = "vehicle",required = true) String vehicle, MultipartFile file){
//        return  ResponseEntity.ok(authService.newDriver(userId,vehicle, file));
//    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/onBoardDriver/{userId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<DriverDto> onBoardDriver(
            @PathVariable Long userId,
            @ModelAttribute DriverVehicleDto driverVehicleDto,
            @RequestParam(value = "file") MultipartFile file){
        System.out.println(userId);
        System.out.println(driverVehicleDto.getVehicle());
        System.out.println(file != null ? file.getOriginalFilename() : null);
        return  ResponseEntity.ok(authService.newDriver(userId, driverVehicleDto.getVehicle(), file));
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
        System.out.println(loginResponseDto.getRefreshToken());
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/refresh-token")
    ResponseEntity<?> refreshToken(HttpServletRequest request,HttpServletResponse response){
       Cookie[] cookies=request.getCookies();
       String token= authService.refreshToken(cookies);
        Map<String,String> result=new HashMap<>();
        result.put("token",token);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/forgot-password")
    ResponseEntity<ApiResponse<Object>> forgotPassword(@Valid  @RequestBody ForgotPasswordEmail forgotPasswordEmail){
        authService.forgotPassword(forgotPasswordEmail.getEmail());
        return ResponseEntity.ok(new ApiResponse<>("OTP sent to registered email address."));
    }
}
