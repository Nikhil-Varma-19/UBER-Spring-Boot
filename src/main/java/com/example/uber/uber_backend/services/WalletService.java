package com.example.uber.uber_backend.services;

import com.example.uber.uber_backend.constants.TransactionMethod;
import com.example.uber.uber_backend.entities.Ride;
import com.example.uber.uber_backend.entities.User;
import com.example.uber.uber_backend.entities.Wallet;

public interface WalletService {
    Wallet createNewWallet(User user);

    Wallet  addMoneyToWallet(Long userId, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    Wallet deductMoneyFromWallet(User user,Double amount,String transactionId, Ride ride, TransactionMethod transactionMethod);

    // to driver only
    void withdrawalAllMoneyToAccount();

    Wallet findByWalletId(Long walletId);

    Wallet findWalletByUser(User user);
}
