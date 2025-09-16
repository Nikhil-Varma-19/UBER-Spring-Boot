package com.example.uber.uber_backend.controllers;

import com.example.uber.uber_backend.dtos.*;
import com.example.uber.uber_backend.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
@Secured("ROLE_DRIVER")
public class DriverController {

    private  final DriverService driverService;

    @PostMapping("/acceptRequest/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRequest(@PathVariable Long rideRequestId){
        return  ResponseEntity.ok(driverService.acceptRide(rideRequestId));
    }

    @PostMapping("/startRide/{rideId}")
    public ResponseEntity<RideDto> startRide(@PathVariable Long rideId, @RequestBody AccptedRideDto accptedRideDto){
       return  ResponseEntity.ok(driverService.startRide(rideId,accptedRideDto.getOtp()));
    }

    @PostMapping("/endRide/{rideId}")
    public ResponseEntity<RideDto> endRide(@PathVariable Long rideId){
     return ResponseEntity.ok(driverService.endRide(rideId));
    }

    @PostMapping("/cancelRide/{rideId}")
    public  ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId){
        return  ResponseEntity.ok(driverService.cancelRide(rideId));
    }

    @PostMapping("/rate-rider")
    public ResponseEntity<RiderDto> rateDriver(@RequestBody RateDto rateDto){
        return ResponseEntity.ok(driverService.rateRider(rateDto.getRideId(),rateDto.getRating()));
    }

    @GetMapping("/my-profile")
    public ResponseEntity<DriverDto> myProfile(){
        return ResponseEntity.ok(driverService.getMyProfileDriver());
    }

    @GetMapping("/my-rides")
    public ResponseEntity<?> myRides(@RequestParam(defaultValue = "0") Integer pageOffSet,@RequestParam(defaultValue = "10") Integer pageSize){
        PageRequest pageRequest=PageRequest.of(pageOffSet,pageSize, Sort.by(Sort.Direction.DESC,"id"));
        return  ResponseEntity.ok(driverService.getAllMyRide(pageRequest));
    }
}
