package com.example.uber.uber_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {

    private Long id;
    private UserDto user;
    private Double rating;
    private  Boolean isAvailable;
    private String vehicleId;

}
