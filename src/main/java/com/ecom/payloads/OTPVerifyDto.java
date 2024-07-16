package com.ecom.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class OTPVerifyDto {
    private int id;
    private String email;
    private int otp;
    private Date entryDate;
}
