package com.example.uber.uber_backend.strategies.impl;


import com.example.uber.uber_backend.entities.RideRequest;
import com.example.uber.uber_backend.services.DistanceService;
import com.example.uber.uber_backend.strategies.RideCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgeCalculationStrategy implements RideCalculationStrategy {
    private final DistanceService distanceService;

    private static final double SURGE_FACTOR=1.5;


    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance=distanceService.calculateDistance(rideRequest.getPickupLocation(),rideRequest.getDropLocation());
        return distance*RIDE_FARE_MULTIPLER*SURGE_FACTOR;
    }
}
