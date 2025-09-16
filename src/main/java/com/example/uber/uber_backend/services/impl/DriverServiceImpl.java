package com.example.uber.uber_backend.services.impl;

import com.example.uber.uber_backend.constants.OTPConstant;
import com.example.uber.uber_backend.constants.RideStatus;
import com.example.uber.uber_backend.constants.RiderRequestStatus;
import com.example.uber.uber_backend.dtos.DriverDto;
import com.example.uber.uber_backend.dtos.RideDto;
import com.example.uber.uber_backend.dtos.RiderDto;
import com.example.uber.uber_backend.entities.*;
import com.example.uber.uber_backend.exceptions.ResourceNotFound;
import com.example.uber.uber_backend.exceptions.RuntimeConflictException;
import com.example.uber.uber_backend.repostities.DriverRepository;
import com.example.uber.uber_backend.repostities.RideRepository;
import com.example.uber.uber_backend.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private  final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final OTPService otpService;
    private final PaymentService paymentService;
    private  final RatingService ratingService;
    @Override
    public RideDto cancelRide(Long rideId) {
        Ride getRide=rideService.getRideById(rideId);
        Driver getDriver=getCurrentDriver();
        if(!getDriver.equals(getRide.getDriver())) throw new RuntimeConflictException("Driver cannot start the ride, has it was not accept by him.");
//       if(!Objects.equals(getDriver.getId(), getRide.getDriver().getId())) throw new RuntimeConflictException("You are not the driver");
       if(!getRide.getRideStatus().equals(RideStatus.COMFIRMED)) throw  new RuntimeConflictException("Ride cannot be cancel now");
       Ride savedRide=rideService.updateRideStatus(getRide,RideStatus.CANCELED);
       driverIsAvailable(getDriver,true);

        return modelMapper.map(savedRide,RideDto.class);
    }

    @Override
    @Transactional
    public RideDto startRide(Long rideId, String otp) {
        otpService.verifyOTP(otp, OTPConstant.OTPType.SEND,String.valueOf(OTPConstant.OTPAction.RIDERREQUEST),rideId);
        Ride getRide=rideService.getRideById(rideId);
        Driver getDriver=getCurrentDriver();
        if(!getDriver.equals(getRide.getDriver())) throw new RuntimeConflictException("Driver cannot start the ride, has it was not accept by him.");
        if(!getRide.getRideStatus().equals(RideStatus.COMFIRMED)) throw new RuntimeConflictException("Ride status is not match");
        getRide.setStartedAt(LocalDateTime.now());
        Ride savedRide=rideService.updateRideStatus(getRide,RideStatus.ONGOIGING);
        paymentService.createNewPayment(savedRide);
        return modelMapper.map(savedRide,RideDto.class);
    }

    @Override
    @Transactional
    public RideDto endRide(Long rideId) {
        Ride getRide=rideService.getRideById(rideId);
        Driver getDriver=getCurrentDriver();
        if(!getDriver.equals(getRide.getDriver()) || !getRide.getRideStatus().equals(RideStatus.ONGOIGING)){
            throw  new RuntimeConflictException("You Cannot End the Ride");
        }
        getRide.setEndedAt(LocalDateTime.now());
        paymentService.processPayment(getRide);
        Ride endRide=rideService.updateRideStatus(getRide,RideStatus.ENEDED);
        driverIsAvailable(getDriver,true);
        return  modelMapper.map(endRide,RideDto.class);
    }

    @Override
    public RideDto acceptRide(Long rideRequestId) {
        RideRequest rideRequest=rideRequestService.findRideRequestById(rideRequestId);

        if(!rideRequest.getRidingStatus().equals(RiderRequestStatus.PENDING)){
            throw  new RuntimeConflictException("The status of ride is not pending.");
        }
        Driver driverAvail=getCurrentDriver();
        if(!driverAvail.getIsAvailable()) throw  new RuntimeConflictException("Driver cannot accept this ride due to un available of it.");
        Driver savedDriver=driverRepository.save(driverAvail);
        Ride newRide=rideService.createNewRide(rideRequest,savedDriver);

        return modelMapper.map(newRide,RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Double rating) {
       Ride ride=  rideService.getRideById(rideId);
        Driver driver=getCurrentDriver();
        if(!driver.equals(ride.getDriver())) throw  new ResourceNotFound("Driver Not Found");
        if(!ride.getRideStatus().equals(RideStatus.ENEDED)) throw  new RuntimeConflictException("Ride is not End");
       Rider rider =ratingService.rateRider(ride,rating);
       return  modelMapper.map(rider,RiderDto.class);
    }

    @Override
    public DriverDto getMyProfileDriver() {
        Driver driver=getCurrentDriver();
        return modelMapper.map(driver,DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRide(PageRequest pageRequest) {
        Driver driver=getCurrentDriver();
        return rideService.getAllRideOfDriver(driver,pageRequest)
                .map(ride -> modelMapper.map(ride,RideDto.class));

    }

    @Override
    public Driver getCurrentDriver() {

        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Driver driver=driverRepository.findByUserId(user.getId()).orElse(null);
        if(driver == null) throw  new ResourceNotFound("Driver Not Found");
        return driver;
    }


    @Override
    public Driver driverIsAvailable(Driver driver, boolean isAvailable) {
        driver.setIsAvailable(isAvailable);
        Driver savedDriver= driverRepository.save(driver);
        return savedDriver;
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return  driverRepository.save(driver);
    }


}
