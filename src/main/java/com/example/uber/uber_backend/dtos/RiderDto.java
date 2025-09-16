package com.example.uber.uber_backend.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RiderDto {

    private Long id;
    private UserDto user;
    private Double rating;}
