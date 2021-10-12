package com.example.demo.service;

import com.example.demo.enums.Platform;
import com.example.demo.model.AuthenticationToken;
import com.example.demo.model.Otp;
import com.example.demo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;

import javax.servlet.http.HttpServletRequest;
//import org.springframework.http.ResponseEntity;

public interface UserServiceInterface {

    public ResponseEntity<Object> registerUser(User user);
    AuthenticationToken createAuthenticationToken(User user);

    User findByEmail(String email);
    User saveUser(User user);
     Otp sendOtpOnEmail(String email);
   // Platform getRequestedClientPlatform(HttpServletRequest request);
}
