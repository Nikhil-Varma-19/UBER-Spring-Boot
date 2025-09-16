package com.example.uber.uber_backend.repostities;

import com.example.uber.uber_backend.entities.Driver;
import com.example.uber.uber_backend.entities.Ride;
import com.example.uber.uber_backend.entities.Rider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride,Long> {
    Page<Ride> findByRider(Rider rider, PageRequest pageRequest);

    Page<Driver> findByDriver(Driver driver, PageRequest pageRequest);
}
