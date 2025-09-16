package com.example.uber.uber_backend.utilis;

import java.security.SecureRandom;

public class OTPGenerate {
    private static final int otpLength=4;
    private static final SecureRandom secureRandom=new SecureRandom();
    private static final String digit="0123456789";

    public static String generateOtp(int otpLength){

        StringBuilder otp=new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            otp.append(digit.charAt(secureRandom.nextInt(digit.length())));
        }
        return  otp.toString();
    }

    public static String generateOtp(){
        return generateOtp(otpLength);
    }

}
