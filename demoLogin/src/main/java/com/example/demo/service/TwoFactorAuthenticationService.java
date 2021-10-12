package com.example.demo.service;

import com.example.demo.model.Otp;
import com.example.demo.model.User;

public interface TwoFactorAuthenticationService {
    Otp sendOtpForTwoFactorAuthentication(User user);
}
