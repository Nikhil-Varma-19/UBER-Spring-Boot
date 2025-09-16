package com.example.uber.uber_backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {

    @NotBlank(message = "Please Enter your name.")
    private String name;

    @Email(message = "Please Enter the email address.")
    private String email;

    @Size(min = 8,max = 16,message = "Please enter the password between 8 to 16 length.")
    private String password;
}
