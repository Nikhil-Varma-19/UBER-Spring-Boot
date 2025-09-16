package com.example.uber.uber_backend.services.impl;

import com.example.uber.uber_backend.constants.OTPConstant;
import com.example.uber.uber_backend.constants.RideStatus;
import com.example.uber.uber_backend.constants.RiderRequestStatus;
import com.example.uber.uber_backend.entities.Driver;
import com.example.uber.uber_backend.entities.Ride;
import com.example.uber.uber_backend.entities.RideRequest;
import com.example.uber.uber_backend.entities.Rider;
import com.example.uber.uber_backend.exceptions.ResourceNotFound;
import com.example.uber.uber_backend.exceptions.RuntimeConflictException;
import com.example.uber.uber_backend.repostities.RideRepository;
import com.example.uber.uber_backend.services.OTPService;
import com.example.uber.uber_backend.services.RideRequestService;
import com.example.uber.uber_backend.services.RideService;
import com.example.uber.uber_backend.utilis.OTPGenerate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final ModelMapper modelMapper;
    private final RideRepository rideRepository;
    private final RideRequestService rideRequestService;
    private final OTPService otpService;
    @Override
    public Ride getRideById(Long rideId) {
        Optional<Ride> ride=rideRepository.findById(rideId);
        if(ride.isEmpty()) throw  new ResourceNotFound("Ride Not Found");
        return ride.get();
    }


    @Override
    @Transactional
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRidingStatus(RiderRequestStatus.ACCEPTED);
        Ride createRide=modelMapper.map(rideRequest,Ride.class);
        createRide.setRideStatus(RideStatus.COMFIRMED);
        createRide.setDriver(driver);
        String otp=OTPGenerate.generateOtp();
        createRide.setId(null);
        Ride newRide=rideRepository.save(createRide);
        if(newRide.getId() == null) throw  new RuntimeConflictException("Ride not Created");
        otpService.createOTP(otp, OTPConstant.OTPType.SEND, String.valueOf(OTPConstant.OTPAction.RIDERREQUEST), newRide.getId());
        rideRequestService.update(rideRequest);
        return newRide;
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        Ride savedRide=rideRepository.save(ride);
        if(savedRide.getId() == null) throw  new RuntimeConflictException("Ride Not Found");
        return savedRide;
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {
     rideRepository.findByRider(rider,pageRequest);
        return null;
    }

    @Override
    public Page<Ride> getAllRideOfDriver(Driver driver, PageRequest pageRequest) {
        rideRepository.findByDriver(driver,pageRequest);
        return null;
    }

}
