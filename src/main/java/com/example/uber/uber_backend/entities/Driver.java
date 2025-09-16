package com.example.uber.uber_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;


@Entity
@Table(name = "driver")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private  Double rating;

    @Column(name = "is_available")
    private  Boolean isAvailable;

    private String vehicleId;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point currentLocation;
}
