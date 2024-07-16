package com.ecom.controllers;

import com.ecom.payloads.OTPVerifyDto;
import com.ecom.payloads.UserDto;
import com.ecom.services.OTPVerifyService;
import com.ecom.services.UserService;
import com.ecom.services.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/auth/otp/")
@CrossOrigin("*")
public class EmailVerifyController {
    int min = 100000;
    int max = 999999;


    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private OTPVerifyService otpService;



    //Send Otp
    @PostMapping("/{email}")
    public boolean sendOTP(@PathVariable String email, HttpSession session)
    {
        System.out.println("Email is : "+ email);

        //Generate OTP with 6 Digit
        int otp =(int)(Math.random()*(max-min+1)+min);
        System.out.println("OTP is : "+otp);

        //Saved OTP to database
        OTPVerifyDto otpVerifyDto = new OTPVerifyDto();
        otpVerifyDto.setOtp(otp);
        otpVerifyDto.setEmail(email);
        boolean otp1 = this.otpService.createOtp(otpVerifyDto);


        //write code for send otp  to email ....

        String subject="OTP Verification From LittleHeart";
        String message=""
                +"<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">"
                +"<div style=\"margin:50px auto;width:70%;padding:20px 0\">"
                +"<div style=\"border-bottom:1px solid #eee\">"
                +"<a href=\"\" style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\">LittleHeart</a>"
                +"</div>"
                +"<p style=\"font-size:1.1em\">Hi,</p>"
                +"<p>Thank you for choosing LittleHeart. Use the following OTP to complete your Sign Up procedures. OTP is valid for 5 minutes</p>"
                +"<h2 style=\"background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;\">"+otp
                +"</h2>"
                +"<p style=\"font-size:0.9em;\">Regards,<br />LittleHeart</p>"
                +"<hr style=\"border:none;border-top:1px solid #eee\" />"
                +"<div style=\"float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300\">"
                +"<p>LittleHeart</p>"
                +"<p>Raghunathpur Jhargram</p>"
                +"<p>West Bengal</p>"
                +"</div>"
                +"<div>"
                +"</div>";

        String to = email;

        boolean flag = this.emailService.sendEmail(to, subject, message);


        return flag;
    }

    //Verify OTP
    @PostMapping("/verify/{otp}")
    public boolean verifyOTP(@PathVariable int otp)
    {
        boolean otp1 = this.otpService.getOtp(otp);
        return otp1;
    }

    //Get User By Email
    /*@GetMapping("/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email)
    {
        return ResponseEntity.ok(this.userService.getUserByEmail(email));
    }*/
    @GetMapping("/{email}")
    public boolean getUser(@PathVariable String email)
    {
        try {
            UserDto user = this.userService.getUserByEmail(email);
            return true;
        }catch (Exception e)
        {
            System.out.println("Email Id Not Found !!"+e.getMessage());
            return false;
        }
    }

}
