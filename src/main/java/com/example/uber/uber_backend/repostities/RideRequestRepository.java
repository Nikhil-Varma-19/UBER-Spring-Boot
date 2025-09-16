package com.example.uber.uber_backend.repostities;

import com.example.uber.uber_backend.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRequestRepository extends JpaRepository<RideRequest,Long> {
}
