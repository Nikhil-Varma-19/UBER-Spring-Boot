package com.example.uber.uber_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DriverVehicleDto {
   @NotBlank
   private String vehicle;
//   private MultipartFile file;

}
