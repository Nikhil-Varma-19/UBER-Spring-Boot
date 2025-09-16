package com.example.uber.uber_backend.services.impl;

import com.example.uber.uber_backend.entities.RideRequest;
import com.example.uber.uber_backend.exceptions.ResourceNotFound;
import com.example.uber.uber_backend.repostities.RideRequestRepository;
import com.example.uber.uber_backend.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;
    private  final ModelMapper modelMapper;
    @Override
    public RideRequest findRideRequestById(Long id) {
        Optional<RideRequest> rideRequestId=rideRequestRepository.findById(id);
        if(rideRequestId.isEmpty()) {
            throw  new ResourceNotFound("Ride Request Not Found");
        }
        return rideRequestId.get();
    }

    @Override
    public void update(RideRequest rideRequest) {
        findRideRequestById(rideRequest.getId());
        RideRequest updatedRideRequest=rideRequestRepository.save(rideRequest);
//        if(updatedRideRequest == null)   throw new RuntimeException("Failed to update RideRequest with ID: " + rideRequest.getId());


    }
}
