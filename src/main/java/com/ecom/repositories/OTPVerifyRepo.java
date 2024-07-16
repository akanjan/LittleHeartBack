package com.ecom.repositories;

import com.ecom.entities.OTPVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OTPVerifyRepo extends JpaRepository<OTPVerify, String> {
    Optional<OTPVerify> findByEmail(String email);
    @Query(value = "SELECT otp FROM otp_verification e where e.otp =:OTP ",
            nativeQuery = true)
    public int getOtp(@Param("OTP") int otp);
}
