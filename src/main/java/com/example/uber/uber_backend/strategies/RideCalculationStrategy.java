package com.example.uber.uber_backend.strategies;


import com.example.uber.uber_backend.entities.RideRequest;

public interface RideCalculationStrategy {

    double RIDE_FARE_MULTIPLER=10;
    double calculateFare(RideRequest rideRequest);
}
