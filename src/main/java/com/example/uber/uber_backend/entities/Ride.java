package com.example.uber.uber_backend.entities;

import com.example.uber.uber_backend.constants.PaymentMethod;
import com.example.uber.uber_backend.constants.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ride")
public class Ride extends DBCommon {
    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @ManyToOne(fetch = FetchType.LAZY)
    private Driver driver;

    @Column(columnDefinition = "Geometry(Point,4326)",name = "pick_up_location")
    private Point pickupLocation;

    @Column(columnDefinition = "Geometry(Point,4326)",name = "drop_location")
    private Point dropLocation;

    @CreationTimestamp
    @Column(name = "request_time")
    private LocalDateTime requestTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "ride_status")
    private RideStatus rideStatus;

    private Double fare;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt ;
}
