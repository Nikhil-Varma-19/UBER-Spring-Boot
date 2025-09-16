package com.example.uber.uber_backend.repostities;

import com.example.uber.uber_backend.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RiderRepository extends JpaRepository<Rider,Long> {

    Optional<Rider> findByUserId(Long id);
}
