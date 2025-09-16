package com.example.uber.uber_backend.services.impl;

import com.example.uber.uber_backend.constants.PaymentStatus;
import com.example.uber.uber_backend.entities.Payment;
import com.example.uber.uber_backend.entities.Ride;
import com.example.uber.uber_backend.exceptions.ResourceNotFound;
import com.example.uber.uber_backend.exceptions.RuntimeConflictException;
import com.example.uber.uber_backend.repostities.PaymentRepository;
import com.example.uber.uber_backend.services.PaymentService;
import com.example.uber.uber_backend.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private  final PaymentRepository paymentRepository;
    private  final PaymentStrategyManager paymentStrategyManager;
    @Override
    public void processPayment(Ride ride) {
        Optional<Payment> payment=paymentRepository.findByRide(ride);
        if(payment.isEmpty()) throw new ResourceNotFound("Payment Not Found");
        paymentStrategyManager.paymentStrategy(ride.getPaymentMethod()).processPayment(payment.get());
        updatePaymentStatus(payment.get(),PaymentStatus.COMFIRMED);
    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment=Payment.builder().paymentStatus(PaymentStatus.PENDING).amount(ride.getFare()).ride(ride).build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);
    }
}
