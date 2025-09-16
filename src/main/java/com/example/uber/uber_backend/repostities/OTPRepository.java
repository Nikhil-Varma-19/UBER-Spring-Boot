package com.example.uber.uber_backend.repostities;

import com.example.uber.uber_backend.constants.OTPConstant;
import com.example.uber.uber_backend.entities.OTPDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTPDB,Long> {

    Optional<OTPDB> findByOtpAndTypeAndActionAndIsActiveTrueAndCreatedBy(String otpValue, OTPConstant.OTPType type, String action,Long createdBy);
}
