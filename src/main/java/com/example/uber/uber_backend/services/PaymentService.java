package com.example.uber.uber_backend.services;

import com.example.uber.uber_backend.constants.PaymentStatus;
import com.example.uber.uber_backend.entities.Payment;
import com.example.uber.uber_backend.entities.Ride;

public interface PaymentService {
    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);
}
