package com.example.uber.uber_backend.services;

import com.example.uber.uber_backend.entities.WalletTransaction;

public interface WalletTransactionService {

    void createNewWalletTransaction(WalletTransaction walletTransaction);
}
