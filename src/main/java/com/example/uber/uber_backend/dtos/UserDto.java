package com.example.uber.uber_backend.dtos;

import com.example.uber.uber_backend.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
//    private  Long id;
    private String name;
    private String email;
    private Set<Role> roles;
}
