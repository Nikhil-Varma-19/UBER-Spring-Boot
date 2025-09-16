package com.example.uber.uber_backend.services;

import com.example.uber.uber_backend.dtos.DriverDto;
import com.example.uber.uber_backend.dtos.RideDto;
import com.example.uber.uber_backend.dtos.RideRequestDto;
import com.example.uber.uber_backend.dtos.RiderDto;
import com.example.uber.uber_backend.entities.Rider;
import com.example.uber.uber_backend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    DriverDto rateDriver(Long rideId, Double rating);

    RiderDto getMyProfileRider();

    Page<RideDto> getAllMyRider(PageRequest pageRequest);

    Rider createNewRider(User user);

    Rider getCurrentRider();
}
