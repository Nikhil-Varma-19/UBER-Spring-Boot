package com.example.uber.uber_backend.services.impl;

import com.example.uber.uber_backend.dtos.DriverDto;
import com.example.uber.uber_backend.entities.Driver;
import com.example.uber.uber_backend.entities.Rating;
import com.example.uber.uber_backend.entities.Ride;
import com.example.uber.uber_backend.entities.Rider;
import com.example.uber.uber_backend.exceptions.ResourceNotFound;
import com.example.uber.uber_backend.repostities.DriverRepository;
import com.example.uber.uber_backend.repostities.RatingRepository;
import com.example.uber.uber_backend.repostities.RiderRepository;
import com.example.uber.uber_backend.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private  final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    @Override
    public Driver rateDriver(Ride ride, Double rating) {
        Optional<Rating> getRating=ratingRepository.findByRide(ride);
        if(getRating.isEmpty()) throw  new ResourceNotFound("Rating Not Found");
        getRating.get().setDriver(ride.getDriver());
        getRating.get().setDriverRating(rating);
        ratingRepository.save(getRating.get());

        Double newAvgDriver=ratingRepository.findByDriver(ride.getDriver()).stream().mapToDouble(Rating::getDriverRating).average().orElse(0.0);
        ride.getDriver().setRating(newAvgDriver);
       return    driverRepository.save(ride.getDriver());

    }

    @Override
    public Rider rateRider(Ride ride, Double rating) {
        Optional<Rating> getRating=ratingRepository.findByRide(ride);
        if(getRating.isEmpty()) throw  new ResourceNotFound("Rating Not Found");
        getRating.get().setRider(ride.getRider());
        getRating.get().setDriverRating(rating);
        ratingRepository.save(getRating.get());

        Double newAvgDriver=ratingRepository.findByRider(ride.getRider()).stream().mapToDouble(Rating::getDriverRating).average().orElse(0.0);
        ride.getRider().setRating(newAvgDriver);
         return  riderRepository.save(ride.getRider());

    }

    @Override
    public Rating createNewRating(Ride ride) {
        Rating rating= Rating.builder()
                .driver(ride.getDriver()).rider(ride.getRider())
                .build();
        return  ratingRepository.save(rating);
    }
}
