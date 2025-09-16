package com.example.uber.uber_backend.strategies;

import com.example.uber.uber_backend.entities.Payment;

public interface PaymentStrategy {

    Double PLATFROM_CHARGE=0.3;

    void processPayment(Payment payment);
}
