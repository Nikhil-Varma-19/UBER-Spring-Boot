package com.example.uber.uber_backend.repostities;

import com.example.uber.uber_backend.entities.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


// ST_Distance(p1,p2)
// ST_DWithin(p1,p2,1000)
public interface DriverRepository extends JpaRepository<Driver,Long> {

    @Query(value = "SELECT d.*, ST_Distance(d.current_location,:pickupLocation) AS distance "+
            "from driver d where is_available=true and ST_DWithin(d.current_location,:pickupLocation,10000)" +
            " ORDER BY distance desc" +
            " LIMIT 10",nativeQuery = true)
    List<Driver> findTenNearDrivers(Point pickupLocation);


    @Query(value = "SELECT d.*, ST_Distance(d.current_location,:pickupLocation) AS distance "+
            "from driver d where is_available=true and ST_DWithin(d.current_location,:pickupLocation,10000)" +
            " ORDER BY rating desc" +
            " LIMIT 10",nativeQuery = true)
    List<Driver> findTopTenNearDriver(Point pickupLocation);

    Optional<Driver> findByUserId(Long id);
}
