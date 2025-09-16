package com.example.uber.uber_backend.services;

import com.example.uber.uber_backend.dtos.DriverDto;
import com.example.uber.uber_backend.dtos.RideDto;
import com.example.uber.uber_backend.dtos.RiderDto;
import com.example.uber.uber_backend.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DriverService {

    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId, String otp);

    RideDto endRide(Long rideId);

    RideDto acceptRide(Long rideRequestId);

    RiderDto rateRider(Long rideId,Double rating);

    DriverDto getMyProfileDriver();

    Page<RideDto> getAllMyRide(PageRequest pageRequest);

    Driver getCurrentDriver();

    Driver driverIsAvailable(Driver driver,boolean isAvailable);

    Driver createNewDriver(Driver driver);



}
