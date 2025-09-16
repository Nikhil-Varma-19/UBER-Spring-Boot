package com.example.uber.uber_backend.repostities;

import com.example.uber.uber_backend.entities.Driver;
import com.example.uber.uber_backend.entities.Rating;
import com.example.uber.uber_backend.entities.Ride;
import com.example.uber.uber_backend.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Driver driver);

    Optional<Rating> findByRide(Ride ride);
}

