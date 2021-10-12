package com.example.demo.service;

import com.example.demo.model.Otp;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;


public interface OtpService {

    public Otp setIsVerified(Otp otpObject);
    public Otp findByOTPNumber(Integer otp);
public void deleteOtpObject(Otp otp);
public Otp findByOtpAndUser(Integer otp, User user);

public void saveOtp(Otp otpObject);
public boolean validateExpiryOfOtp(Otp otpObj);
}
