package com.example.uber.uber_backend.strategies.impl;

import com.example.uber.uber_backend.entities.RideRequest;
import com.example.uber.uber_backend.services.DistanceService;
import com.example.uber.uber_backend.strategies.RideCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RideCalculationStrategyImpl implements RideCalculationStrategy {

    private final DistanceService distanceService;


    @Override
    public double calculateFare(RideRequest rideRequest) {
        Double distance=distanceService.calculateDistance(rideRequest.getPickupLocation(),rideRequest.getDropLocation());
        return distance*RIDE_FARE_MULTIPLER;
    }
}
