package com.example.uber.uber_backend.strategies.impl;

import com.example.uber.uber_backend.constants.PaymentStatus;
import com.example.uber.uber_backend.constants.TransactionMethod;
import com.example.uber.uber_backend.entities.Driver;
import com.example.uber.uber_backend.entities.Payment;
import com.example.uber.uber_backend.entities.Rider;
import com.example.uber.uber_backend.services.PaymentService;
import com.example.uber.uber_backend.services.WalletService;
import com.example.uber.uber_backend.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    @Override
    public void processPayment(Payment payment) {
    Driver driver=payment.getRide().getDriver();
    Rider rider=payment.getRide().getRider();

    // deduct from rider wallet
    walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(),null,payment.getRide(), TransactionMethod.RIDING);

    double charge=payment.getAmount() * (1-PLATFROM_CHARGE);
    // add money to driver waller
    walletService.addMoneyToWallet(driver.getUser().getId(),charge,null,payment.getRide(),TransactionMethod.RIDING);
    }
}
