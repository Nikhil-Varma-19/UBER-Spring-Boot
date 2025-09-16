package com.example.uber.uber_backend.services;

import org.locationtech.jts.geom.Point;

public interface DistanceService {
// osrm

    double calculateDistance(Point src,Point dest);
}
