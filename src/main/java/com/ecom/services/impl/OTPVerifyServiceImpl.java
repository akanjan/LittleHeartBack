package com.ecom.services.impl;

import com.ecom.entities.OTPVerify;
import com.ecom.exceptions.ResourceNotFoundException;
import com.ecom.payloads.OTPVerifyDto;
import com.ecom.repositories.OTPVerifyRepo;
import com.ecom.services.OTPVerifyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class OTPVerifyServiceImpl implements OTPVerifyService {
    @Autowired
    private OTPVerifyRepo otpVerifyRepo;

    @Autowired
    private ModelMapper modelMapper;


    //Save OTP
    @Override
    public boolean createOtp(@NotNull OTPVerifyDto otpVerifyDto) {
        try {
            OTPVerify otpVerify = this.modelMapper.map(otpVerifyDto, OTPVerify.class);
            otpVerify.setEntryDate(new Timestamp(new Date().getTime()));
            OTPVerify savedOtp = this.otpVerifyRepo.save(otpVerify);
            this.modelMapper.map(savedOtp, OTPVerifyDto.class);
            return true;
        }catch (Exception e)
        {
            return false;
        }

    }

    @Override
    public void deleteOtp(String email) {
        OTPVerify otpVerify = this.otpVerifyRepo.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email", "Email Id", 0));
        this.otpVerifyRepo.delete(otpVerify);
    }


    @Override
    public boolean getOtp(int otp) {
        //String otp1 = this.otpVerifyRepo.findByOtp(otp);
        boolean OTPResult=false;
        try {
            int otp1 = this.otpVerifyRepo.getOtp(otp);
            System.out.println("Get OTP From DataBase :: "+otp1);
            if(otp1==otp)
            {
                OTPResult=true;
            }
        }catch (Exception e)
        {
            System.out.println("You are enter wrong otp !!" +e.getMessage());
        }
        return OTPResult;
    }

}
