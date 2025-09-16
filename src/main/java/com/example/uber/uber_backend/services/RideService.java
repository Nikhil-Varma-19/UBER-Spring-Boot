package com.example.uber.uber_backend.services;

import com.example.uber.uber_backend.constants.RideStatus;
import com.example.uber.uber_backend.entities.Driver;
import com.example.uber.uber_backend.entities.Ride;
import com.example.uber.uber_backend.entities.RideRequest;
import com.example.uber.uber_backend.entities.Rider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {
    Ride getRideById(Long rideId);

    Ride createNewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);

    Page<Ride> getAllRideOfDriver(Driver driver, PageRequest pageRequest);

}
