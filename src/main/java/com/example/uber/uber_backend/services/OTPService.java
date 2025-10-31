package com.example.uber.uber_backend.services;


import com.example.uber.uber_backend.constants.OTPConstant;
import com.example.uber.uber_backend.entities.OTPDB;
import com.example.uber.uber_backend.exceptions.RuntimeConflictException;
import com.example.uber.uber_backend.repostities.OTPRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OTPService {
    private final OTPRepository otpRepository;

    public void createOTP(String otpValue, OTPConstant.OTPType type, String action,Long id){
//        OTPConstant.OTPType otpType= OTPConstant.OTPType.valueOf(type);
        OTPDB otp=OTPDB.builder().otp(otpValue).action(action).type(type).createdBy(id).build();
        OTPDB saveOtp=otpRepository.save(otp);
        if(saveOtp.getId() == null) throw  new RuntimeConflictException("OTP not saved");
    }

//    private static final Set<String> TYPE =
//            Arrays.stream(OTPConstant.OTPType.values())
//                    .map(Enum::name)
//                    .collect(Collectors.toSet());

//    private static boolean exists(String value) {
//        return TYPE.contains(value);
//    }

    public void verifyOTP(String otpValue, OTPConstant.OTPType type,String action,Long id){
        System.out.println("===================="+otpValue+" "+type+" "+action+" "+id+"===================");
        Optional<OTPDB> getOTP=otpRepository.findByOtpAndTypeAndActionAndIsActiveTrueAndCreatedBy(otpValue,type,action,id);

        if(getOTP.isEmpty()) throw  new RuntimeConflictException("OTP is not Valid.");

        LocalDateTime now=LocalDateTime.now();

        if(getOTP.get().getCreatedAt() != null && now.isAfter(getOTP.get().getCreatedAt().plusMinutes(5))){
            throw  new RuntimeConflictException("OTP is Expired.");
        }
        getOTP.get().setIsActive(false);
        getOTP.get().setType(OTPConstant.OTPType.RECEIVED);
        OTPDB savedOTP=otpRepository.save(getOTP.get());
        if(savedOTP.getId() == null) throw  new RuntimeConflictException("OTP not update.");

    }

    public void createForgotPassword(String otpValue,Long userId){
        OTPDB otpdb= OTPDB.builder().createdBy(userId).otp(otpValue).action("OTPPASS").type(OTPConstant.OTPType.SEND).build();
        OTPDB savedOTP=otpRepository.save(otpdb);
        if(savedOTP.getId() == null) throw  new RuntimeConflictException("Problem with OTP Services");
    }

    public void verifyForgotPassword(String otpValue,Long userId){
        Optional<OTPDB> findOTP=otpRepository.findByOtpAndTypeAndActionAndIsActiveTrueAndCreatedBy(otpValue, OTPConstant.OTPType.SEND,"OTPPASS",userId);
        if(findOTP.isEmpty()) throw  new RuntimeConflictException("OTP is not found");
        LocalDateTime now=LocalDateTime.now();
        if(findOTP.get().getCreatedBy() != null &&  now.isAfter(findOTP.get().getCreatedAt().plusMinutes(2))){
            throw  new RuntimeConflictException("OTP is Expired.");
        }
        findOTP.get().setIsActive(false);
        findOTP.get().setType(OTPConstant.OTPType.RECEIVED);
        OTPDB savedOTP=otpRepository.save(findOTP.get());
        if(savedOTP.getId() == null) throw  new RuntimeConflictException("Problem with OTP Services");
    }









}
