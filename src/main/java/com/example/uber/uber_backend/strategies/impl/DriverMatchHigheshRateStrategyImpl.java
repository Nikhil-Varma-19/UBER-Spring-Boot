package com.example.uber.uber_backend.strategies.impl;

import com.example.uber.uber_backend.entities.Driver;
import com.example.uber.uber_backend.entities.RideRequest;
import com.example.uber.uber_backend.repostities.DriverRepository;
import com.example.uber.uber_backend.strategies.DriverMatchStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchHigheshRateStrategyImpl implements DriverMatchStrategy {

    private  final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchDriver(RideRequest rideRequest) {
        return driverRepository.findTopTenNearDriver(rideRequest.getPickupLocation());
    }
}
