package com.example.uber.uber_backend.entities;


import com.example.uber.uber_backend.constants.PaymentMethod;
import com.example.uber.uber_backend.constants.RiderRequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "rider_request")
public class RideRequest extends DBCommon {

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @Column(columnDefinition = "Geometry(Point,4326)",name = "pick_up_location")
    private Point pickupLocation;

    @Column(columnDefinition = "Geometry(Point,4326)",name = "drop_location")
    private Point dropLocation;

    private Double fare;

    @CreationTimestamp
    @Column(name = "request_time")
    private LocalDateTime requestTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "riding_status")
    private RiderRequestStatus ridingStatus;
}
