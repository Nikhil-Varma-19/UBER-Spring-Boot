package com.example.uber.uber_backend.services.impl;

import com.example.uber.uber_backend.services.DistanceService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistanceServiceOSRMImpl implements DistanceService {

    private static final String OSRM_URL="http://router.project-osrm.org/route/v1/driving/";
    @Override
    public double calculateDistance(Point src, Point dest) {
        String uri=src.getX()+","+src.getY()+";"+dest.getX()+","+dest.getY();
        // CALL THE  OSRM API
        try{
            OSRMResponse response=RestClient.builder().baseUrl(OSRM_URL).build().get().uri(uri).retrieve().body(OSRMResponse.class);
            return response.getRoutes().getFirst().getDistance() /1000.0;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while calling the osrm api "+e.getMessage());
        }
    }
}


@Data
class OSRMResponse{
  private List<OSRMRoutes> routes;
}

@Data
class OSRMRoutes{
    private Double distance;
}