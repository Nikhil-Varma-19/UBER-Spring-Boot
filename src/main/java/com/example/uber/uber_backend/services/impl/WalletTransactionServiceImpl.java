package com.example.uber.uber_backend.services.impl;

import com.example.uber.uber_backend.entities.WalletTransaction;
import com.example.uber.uber_backend.exceptions.RuntimeConflictException;
import com.example.uber.uber_backend.repostities.WalletTransactionRepository;
import com.example.uber.uber_backend.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
      WalletTransaction NewwalletTransaction=walletTransactionRepository.save(walletTransaction);
      if(NewwalletTransaction.getId() == null) throw  new RuntimeConflictException("Wallet Transaction Not Save");
    }
}
