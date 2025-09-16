package com.example.uber.uber_backend.dtos;

import com.example.uber.uber_backend.constants.TransactionMethod;
import com.example.uber.uber_backend.constants.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransactionDto {
    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private  String transactionId;

    private WalletDto wallet ;

    private RideDto ride;

    private LocalDateTime timeStamp;
}
