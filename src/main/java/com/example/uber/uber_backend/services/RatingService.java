package com.example.uber.uber_backend.services;


import com.example.uber.uber_backend.dtos.DriverDto;
import com.example.uber.uber_backend.entities.Driver;
import com.example.uber.uber_backend.entities.Rating;
import com.example.uber.uber_backend.entities.Ride;
import com.example.uber.uber_backend.entities.Rider;

public interface RatingService {

    Driver rateDriver(Ride ride, Double rating);
    Rider rateRider(Ride ride, Double rating);
    Rating createNewRating(Ride ride);
}
