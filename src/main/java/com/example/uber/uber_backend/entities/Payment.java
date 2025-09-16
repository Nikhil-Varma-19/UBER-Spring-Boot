package com.example.uber.uber_backend.entities;

import com.example.uber.uber_backend.constants.PaymentMethod;
import com.example.uber.uber_backend.constants.PaymentStatus;
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
public class Payment extends DBCommon{

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne(fetch = FetchType.LAZY)
    private Ride ride;

    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @CreationTimestamp
    @Column(name = "payment_time")
    private LocalDateTime paymentTime;
}
