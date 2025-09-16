package com.example.uber.uber_backend.entities;


import com.example.uber.uber_backend.constants.OTPConstant;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "otp")
@EntityListeners(AuditingEntityListener.class)
public class OTPDB  extends DBCommon{

    private String otp;

    @Enumerated(EnumType.STRING)
    private OTPConstant.OTPType type;

    private String action;

    @Column(name = "created_by")
    private Long createdBy;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}




