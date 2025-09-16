package com.example.uber.uber_backend.dtos;

import com.example.uber.uber_backend.constants.PaymentMethod;
import com.example.uber.uber_backend.constants.RideStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RideDto {

    private  Long id;
    private DriverDto driver;

    private PointDto pickupLocation;

    private PointDto dropLocation;

    private LocalDateTime requestTime;

    private PaymentMethod paymentMethod;

    private RiderDto rider;

    private RideStatus rideStatus;

    private Double fare;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt ;

}
