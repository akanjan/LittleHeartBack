package com.ecom.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "OTP_Verification")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OTPVerify {
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;*/
    @Id
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private int otp;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Verify_Date")
    private Date entryDate;
}
