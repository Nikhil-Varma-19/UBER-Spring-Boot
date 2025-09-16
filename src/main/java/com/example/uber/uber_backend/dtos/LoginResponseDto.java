package com.example.uber.uber_backend.dtos;

import com.example.uber.uber_backend.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoginResponseDto {
    private Set<Role> role;
    private String token;
    private String refreshToken;
}
