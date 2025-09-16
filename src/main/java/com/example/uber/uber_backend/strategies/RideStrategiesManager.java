package com.example.uber.uber_backend.strategies;

import com.example.uber.uber_backend.strategies.impl.DriverMatchHigheshRateStrategyImpl;
import com.example.uber.uber_backend.strategies.impl.DriverMatchNearestStrategyImpl;
import com.example.uber.uber_backend.strategies.impl.RideCalculationStrategyImpl;
import com.example.uber.uber_backend.strategies.impl.RideFareSurgeCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategiesManager {
    private final DriverMatchHigheshRateStrategyImpl driverMatchHigheshRateStrategy;
    private  final DriverMatchNearestStrategyImpl driverMatchNearestStrategy;
    private final RideCalculationStrategyImpl rideCalculationStrategy;
    private final RideFareSurgeCalculationStrategy rideFareSurgeCalculationStrategy;

    public DriverMatchStrategy driverMatchStrategy(double rating){
      if(rating >= 4){
          return  driverMatchHigheshRateStrategy;
      }else {
          return driverMatchNearestStrategy;
      }
    }

    public  RideCalculationStrategy rideCalculationStrategy(){
        LocalTime surgeStartTime=LocalTime.of(18,0);
        LocalTime surgeEndTime=LocalTime.of(21,0);
        LocalTime currentTime=LocalTime.now();
        boolean isSurge=currentTime.isAfter(surgeStartTime) && currentTime.isBefore( surgeEndTime);

        if(isSurge){
            return rideFareSurgeCalculationStrategy;
        }else {
            return rideCalculationStrategy;
        }
    }
}
