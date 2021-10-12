package com.example.demo.service;

import com.example.demo.enums.Platform;
import com.example.demo.model.AuthenticationToken;
import com.example.demo.model.Otp;
import com.example.demo.model.User;
import com.example.demo.repository.AuthenticationRepository;
import com.example.demo.repository.OtpRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service

public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepo;
    private Object TokenGenerator;
    @Autowired
    private AuthenticationRepository authRepository;
    @Autowired
    MailService mailservice;
    @Autowired
    private final JavaMailSender javaMailSender;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private MailService mailService;

    public UserService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public ResponseEntity<Object> registerUser(User user) {
        User savedUser = userRepo.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @Override
    public AuthenticationToken createAuthenticationToken(User user) {
        String token = UUID.randomUUID().toString();
        AuthenticationToken authenticationToken = new AuthenticationToken(token, user);
        AuthenticationToken savedToken = authRepository.saveAndFlush(authenticationToken);
        return savedToken;
    }

    //sendMailForLinkVerification(User user,AuthenticationToken savedAuthToken) {
//        String message = "Please click on the link below to complete your verification process";
//        String Url = "https//localhost:8080";
//        String urlWithToken = Url+savedAuthToken.getToken();
//
//
//
//        mailservice.mailSend(user.getEmail(),message,urlWithToken);
//      //  mailservice.mailSendForLinkVerification(user.getEmail(),message,map);
//    }
    public void sendMailForLinkVerification(User user, AuthenticationToken authenticationToken) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("shweta.singh@oodles.io");
        simpleMailMessage.setTo(user.getEmail());
        String link="https://localhost/8080/"+ authenticationToken.getToken();


        String message = "Verificcaton link  " + link;
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);
        System.out.println("Mail Send !!");
    }

    public User findByEmail(String email) {

        return userRepo.findByEmail(email);

    }

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public Otp sendOtpOnEmail(String email) {
        Random random = new Random();
        int otpNumber = 100000 + random.nextInt(900000);
        Otp otp = new Otp();
        otp.setOtp(otpNumber);
        otp.setEmail(email);
        Otp savedOtp = otpRepository.save(otp);
//        Map<String,Object> map=new HashMap<>() ;
//        map.put("otp = ",savedOtp.getOtp());
//        map.put("email ",email);
        String message = "dear user please this 6 digit code to verify email";
        String subject = "forget password otp";
        String sendOtp = " " + (savedOtp.getOtp());
        mailservice.mailSend(email, subject, sendOtp);

        return savedOtp;

    }
}
