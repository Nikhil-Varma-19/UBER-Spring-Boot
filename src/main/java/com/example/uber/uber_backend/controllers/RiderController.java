package com.example.uber.uber_backend.controllers;


import com.example.uber.uber_backend.dtos.*;
import com.example.uber.uber_backend.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rider")
@RequiredArgsConstructor
@Secured("ROLE_RIDER")
public class RiderController {

    private  final RiderService riderService;

    @PostMapping("requestRide")
    public ResponseEntity<RideRequestDto> requestRide(@RequestBody RideRequestDto rideRequestDto){
       return  ResponseEntity.ok(riderService.requestRide(rideRequestDto));
    }

    @PostMapping("/cancelRide/{rideId}")
    public  ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId){
        return  ResponseEntity.ok(riderService.cancelRide(rideId));
    }

    @PostMapping("/rate-driver")
    public ResponseEntity<DriverDto> rateDriver(@RequestBody RateDto rateDto){
        return ResponseEntity.ok(riderService.rateDriver(rateDto.getRideId(),rateDto.getRating()));
    }

    @GetMapping("/my-profile")
    public ResponseEntity<RiderDto> myProfile(){
        return ResponseEntity.ok(riderService.getMyProfileRider());
    }

    @GetMapping("/my-rides")
    public ResponseEntity<?> myRides(@RequestParam(defaultValue = "0") Integer pageOffSet,@RequestParam(defaultValue = "10") Integer pageSize){
        PageRequest pageRequest=PageRequest.of(pageOffSet,pageSize);
        return  ResponseEntity.ok(riderService.getAllMyRider(pageRequest));
    }



}
