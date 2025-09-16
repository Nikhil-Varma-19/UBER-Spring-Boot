package com.example.uber.uber_backend.entities;

import com.example.uber.uber_backend.constants.TransactionMethod;
import com.example.uber.uber_backend.constants.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "wallet_transaction")
public class WalletTransaction extends DBCommon{

    private Double amount;

    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name=  "transaction_method")
    private TransactionMethod transactionMethod;

    @Column(name = "transaction_id")
    private  String transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet ;


    @ManyToOne
    private Ride ride;

    @CreationTimestamp
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}
