package com.example.demo.service;

import com.example.demo.model.Otp;
import com.example.demo.model.User;
import com.example.demo.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TwoFactorAuthenticationServiceImpl implements TwoFactorAuthenticationService {
    @Autowired
    OtpRepository otpRepository;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    MailService mailService;
    @Override
    public Otp sendOtpForTwoFactorAuthentication(User user) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
if(user.isEmailVerified()){
            String userEmail=user.getEmail();
            Otp objOtp=new Otp();
            objOtp.setOtp(otp);
            objOtp.setUser(user);


          Otp otp1=  otpRepository.save(objOtp);
        String subject="otp send";
        String message=""+otp;
        mailService.mailSend(userEmail,subject,message);
          return otp1;}
else{
    return null;
}




    }
}
