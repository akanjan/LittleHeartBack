package com.ecom.services;

import com.ecom.payloads.OTPVerifyDto;

public interface OTPVerifyService {
    //Create New OTP
    boolean createOtp(OTPVerifyDto otpVerifyDto);
    //Delete Otp
    void deleteOtp(String email);
    //get Otp
    boolean getOtp(int otp);
}
