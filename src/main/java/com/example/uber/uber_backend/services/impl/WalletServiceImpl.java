package com.example.uber.uber_backend.services.impl;

import com.example.uber.uber_backend.constants.TransactionMethod;
import com.example.uber.uber_backend.constants.TransactionType;
import com.example.uber.uber_backend.entities.Ride;
import com.example.uber.uber_backend.entities.User;
import com.example.uber.uber_backend.entities.Wallet;
import com.example.uber.uber_backend.entities.WalletTransaction;
import com.example.uber.uber_backend.exceptions.ResourceNotFound;
import com.example.uber.uber_backend.repostities.WalletRepository;
import com.example.uber.uber_backend.services.WalletService;
import com.example.uber.uber_backend.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private  final ModelMapper modelMapper;
    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;

    @Override
    public Wallet createNewWallet(User user) {
        Wallet newWallet=new Wallet();
        newWallet.setUser(user);
        return walletRepository.save(newWallet);
    }

    @Override
    @Transactional
    public Wallet addMoneyToWallet(Long userId, Double amount, String  transactionId, Ride ride, TransactionMethod transactionMethod) {
        Optional<Wallet> wallet=walletRepository.findByUserId(userId);
        if(wallet.isEmpty()) throw  new ResourceNotFound("Wallet not Found");
        Double currentBalance=wallet.get().getBalance();
        wallet.get().setBalance(currentBalance+amount);

        WalletTransaction CreatewalletTransaction=WalletTransaction.builder().transactionId(transactionId)
                .wallet(wallet.get()).ride(ride).transactionMethod(transactionMethod).transactionType(TransactionType.CREDIT).build();

        walletTransactionService.createNewWalletTransaction(CreatewalletTransaction);
        return walletRepository.save(wallet.get());
    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, Double amount,String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet=findWalletByUser(user);
        double balanceAmount=wallet.getBalance() - amount;
        wallet.setBalance(balanceAmount);

        WalletTransaction walletTransaction=WalletTransaction.builder().transactionId(transactionId).wallet(wallet)
                .ride(ride).transactionMethod(transactionMethod).transactionType(TransactionType.DEBIT).build();
        walletTransactionService.createNewWalletTransaction(walletTransaction);
        return  walletRepository.save(wallet);
    }

    @Override
    public void withdrawalAllMoneyToAccount() {

    }

    @Override
    public Wallet findByWalletId(Long walletId) {
        Optional<Wallet> fetchWallet=walletRepository.findById(walletId);
        if(fetchWallet.isEmpty()) throw new ResourceNotFound("Wallet Not Found");
        return fetchWallet.get();
    }

    @Override
    public Wallet findWalletByUser(User user) {
        Optional<Wallet> wallet=walletRepository.findByUser(user);
        if(wallet.isEmpty()) throw  new ResourceNotFound("User Wallet Not Found");
        return wallet.get();
    }
}
