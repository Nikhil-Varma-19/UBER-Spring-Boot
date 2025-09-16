package com.example.uber.uber_backend.services;

import com.example.uber.uber_backend.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long id);

    void update(RideRequest rideRequest);
}
