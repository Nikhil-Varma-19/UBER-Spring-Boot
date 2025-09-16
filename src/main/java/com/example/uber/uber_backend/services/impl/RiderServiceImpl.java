package com.example.uber.uber_backend.services.impl;

import com.example.uber.uber_backend.constants.RideStatus;
import com.example.uber.uber_backend.constants.RiderRequestStatus;
import com.example.uber.uber_backend.dtos.DriverDto;
import com.example.uber.uber_backend.dtos.RideDto;
import com.example.uber.uber_backend.dtos.RideRequestDto;
import com.example.uber.uber_backend.dtos.RiderDto;
import com.example.uber.uber_backend.entities.*;
import com.example.uber.uber_backend.exceptions.ResourceNotFound;
import com.example.uber.uber_backend.exceptions.RuntimeConflictException;
import com.example.uber.uber_backend.repostities.RideRequestRepository;
import com.example.uber.uber_backend.repostities.RiderRepository;
import com.example.uber.uber_backend.services.DriverService;
import com.example.uber.uber_backend.services.RatingService;
import com.example.uber.uber_backend.services.RideService;
import com.example.uber.uber_backend.services.RiderService;
import com.example.uber.uber_backend.strategies.RideStrategiesManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategiesManager rideStrategiesManager;
    private  final RideRequestRepository rideRequestRepository;
    private  final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;

    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
           Rider currentRider=getCurrentRider();
           RideRequest rideRequest=modelMapper.map(rideRequestDto,RideRequest.class);
           rideRequest.setRidingStatus(RiderRequestStatus.PENDING);
           double fare=rideStrategiesManager.rideCalculationStrategy().calculateFare(rideRequest);
           rideRequest.setFare(fare);
          rideRequest.setRider(currentRider);
           RideRequest savedRideRequest=rideRequestRepository.save(rideRequest);
          List<Driver> drivers= rideStrategiesManager.driverMatchStrategy(currentRider.getRating()).findMatchDriver(rideRequest);
        return modelMapper.map(savedRideRequest,RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider rider=getCurrentRider();
        Ride ride=rideService.getRideById(rideId);

        if(rider.equals(ride.getRider())) throw  new RuntimeConflictException("Rider are not allow to cancel the ride");

        if(!ride.getRideStatus().equals(RideStatus.COMFIRMED)) throw  new RuntimeConflictException("Ride cannot be cancel now");
        Ride savedRide=rideService.updateRideStatus(ride,RideStatus.CANCELED);
        driverService.driverIsAvailable(ride.getDriver(),true);
        
        return null;
    }

    @Override
    public DriverDto rateDriver(Long rideId, Double rating) {
        Ride ride=  rideService.getRideById(rideId);
        Rider rider=getCurrentRider();
        if(!rider.equals(ride.getRider())) throw  new ResourceNotFound("Rider Not Found");
        if(!ride.getRideStatus().equals(RideStatus.ENEDED)) throw  new RuntimeConflictException("Ride is not End");
        Driver driverRating=ratingService.rateDriver(ride,rating);
        return  modelMapper.map(driverRating,DriverDto.class);
    }

    @Override
    public RiderDto getMyProfileRider() {
        Rider rider=getCurrentRider();
        return modelMapper.map(rider,RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRider(PageRequest pageRequest) {
        Rider rider=getCurrentRider();
        return  rideService.getAllRidesOfRider(rider,pageRequest).map(ride -> modelMapper.map(ride,RideDto.class));
    }

    @Override
    public Rider createNewRider(User user) {
        Rider newRider=Rider.builder().user(user).rating(0.0).build();
        return riderRepository.save(newRider);
    }

    @Override
    public Rider getCurrentRider() {
        // todo using spring security
       User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Rider rider=riderRepository.findByUserId(user.getId()).orElse(null);
        if(rider == null) throw  new ResourceNotFound( "Rider Not Found");
        return rider;
    }
}
