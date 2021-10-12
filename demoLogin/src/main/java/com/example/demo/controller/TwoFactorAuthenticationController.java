package com.example.demo.controller;

import com.example.demo.constant.MessageConstant;
import com.example.demo.model.AuthenticationToken;
import com.example.demo.model.Otp;
import com.example.demo.model.User;
import com.example.demo.repository.AuthenticationRepository;
import com.example.demo.repository.OtpRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.*;
import com.example.demo.utilPackage.LocalService;
import com.example.demo.utilPackage.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@RestController
public class TwoFactorAuthenticationController {
    @Autowired
    AuthenticationRepository authenticationRepository;
    @Autowired
    TwoFactorAuthenticationService twoFactorAuthenticationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationServiceImpl authenticationService;
    @Autowired
    OtpService otpService;
    @Autowired
    UserService userService;
    @Autowired
    OtpRepository otpRepository;
    @Autowired
    MailService mailService;
    @Autowired
    LocalService localService;
    @PutMapping("/send2FAOtp")
    public ResponseEntity<Object> sendOtpForTwoFactorAuthentication(@RequestParam String twoFaTempToken){
        AuthenticationToken token=authenticationRepository.findByToken(twoFaTempToken);
        if(Objects.isNull(token)){
            ResponseHandler.response(HttpStatus.BAD_REQUEST,true,localService.getMessage(MessageConstant.TOKEN_INVALID));
        }
        if(token.isDeleted()){
            ResponseHandler.response(HttpStatus.BAD_REQUEST,true,localService.getMessage(MessageConstant.TOKEN_INVALID));
        }
        User user=token.getUser();
        if(Objects.isNull(user)){
            return ResponseHandler.response(HttpStatus.NOT_FOUND,true,localService.getMessage(MessageConstant.USER_NOT_FOUND));
        }
        if(!user.isUserRegistered()){
            return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,localService.getMessage(MessageConstant.USER_NOT_REGISTERED));}
            Otp otpObject=twoFactorAuthenticationService.sendOtpForTwoFactorAuthentication(user);
//        String  msg=otpObject.getOtp()+"";
//        mailService.mailSend(user.getEmail(),"otp sending","msg");



            user=userRepository.save(user);
            if(Objects.nonNull(otpObject)){
                return ResponseHandler.response(HttpStatus.OK,false,"otp send succesfully");
            }


return  ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"unable to send otp");
    }
    @PutMapping("verify2FA/otp")
    public ResponseEntity<Object> verifyOtp(@RequestParam int otp, @RequestParam String twoFaTempToken, HttpServletRequest request){
if ((twoFaTempToken.isEmpty())){
    ResponseHandler.response(HttpStatus.BAD_REQUEST,true,localService.getMessage(MessageConstant.TOKEN_INVALID));
}
AuthenticationToken authenticationToken=authenticationRepository.findByToken(twoFaTempToken);
if(Objects.isNull(authenticationToken)){
    ResponseHandler.response(HttpStatus.BAD_REQUEST,true,localService.getMessage(MessageConstant.TOKEN_INVALID));
}
User user=authenticationToken.getUser();

if(!user.isUserRegistered()){
    return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,localService.getMessage(MessageConstant.USER_NOT_REGISTERED));
}

Otp otpObj=otpService.findByOtpAndUser(otp,user);
System.out.println(otpObj);



    User getUser=otpObj.getUser();
    if(getUser.equals(user)){
        otpObj.setDeleted(true);
        otpObj.setVerfied(true);

        //AuthenticationToken token=userService.createAuthenticationToken(user);
        AuthenticationToken authToken=authenticationService.login(authenticationToken.getUser().getPassword(),user);

        Map map = new TreeMap();
        map.put("authToken",authToken);
        map.put("User",user);
        authenticationRepository.save(authToken);
        userRepository.save(user);

        return ResponseHandler.response(HttpStatus.OK,false,"otp verify success",map);

    }



return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"otp not verify");




    }
    @GetMapping("/authToken")
    public AuthenticationToken getAuthenticationToken( @RequestParam  String authenticationToken){
        return authenticationRepository.findByToken(authenticationToken);
    }

}



