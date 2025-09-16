package com.example.uber.uber_backend.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rating extends  DBCommon{

    @OneToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;


    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "rider_id")
    private Rider rider;

    @Column(name = "driver_rating")
    private Double driverRating;

    @Column(name = "rider_rating")
    private Double riderRating;

}
