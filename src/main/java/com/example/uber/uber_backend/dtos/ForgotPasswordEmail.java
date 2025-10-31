package com.example.uber.uber_backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordEmail {

    @NotBlank(message = "User email cannot be blank")
    @Email(message = "User email should be a valid email")
    private  String email;
}
