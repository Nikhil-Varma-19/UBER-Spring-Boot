package com.example.uber.uber_backend.strategies;

import com.example.uber.uber_backend.entities.Driver;
import com.example.uber.uber_backend.entities.RideRequest;

import java.util.List;

public interface DriverMatchStrategy {

     List<Driver> findMatchDriver(RideRequest rideRequest);
}
