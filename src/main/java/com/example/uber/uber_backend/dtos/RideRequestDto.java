package com.example.uber.uber_backend.dtos;

import com.example.uber.uber_backend.constants.PaymentMethod;
import com.example.uber.uber_backend.constants.RiderRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {


    private  Long id;

    private RiderDto rider;

    private PointDto pickupLocation;

    private PointDto dropLocation;

    private PaymentMethod paymentMethod;


    private LocalDateTime requestTime;

   private double fare;

    private RiderRequestStatus ridingStatus;
}
