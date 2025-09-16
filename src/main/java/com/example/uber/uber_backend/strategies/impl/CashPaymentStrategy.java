package com.example.uber.uber_backend.strategies.impl;

import com.example.uber.uber_backend.constants.PaymentStatus;
import com.example.uber.uber_backend.constants.TransactionMethod;
import com.example.uber.uber_backend.entities.Payment;
import com.example.uber.uber_backend.entities.Driver;
import com.example.uber.uber_backend.entities.Wallet;
import com.example.uber.uber_backend.services.PaymentService;
import com.example.uber.uber_backend.services.WalletService;
import com.example.uber.uber_backend.strategies.PaymentStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private  final WalletService walletService;


    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver=payment.getRide().getDriver();
        double totalCharge=payment.getAmount() * PLATFROM_CHARGE;
        walletService.deductMoneyFromWallet(driver.getUser(), totalCharge,null, payment.getRide(),TransactionMethod.RIDING);
    }
}
