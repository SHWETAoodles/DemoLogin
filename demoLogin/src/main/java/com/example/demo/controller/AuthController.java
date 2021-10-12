package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.AuthenticationToken;
import com.example.demo.model.Otp;
import com.example.demo.model.User;
import com.example.demo.repository.AuthenticationRepository;
import com.example.demo.repository.OtpRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.*;
import com.example.demo.utilPackage.AuthenticationResponse;
import com.example.demo.utilPackage.ResponseHandler;
import com.example.demo.utilPackage.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@RestController
public class AuthController {
    @Autowired
    AuthenticationServiceImpl authenticationService;
    @Autowired
    UserServiceInterface userService;
    @Autowired
    SendMailServiceImpl mailService;
    @Autowired
    AuthenticationRepository authenticationTokenrepo;
    @Autowired
    OtpService otpService;
    @Autowired
    UserRepository userRepository;


    @PostMapping("/verfication")
    public ResponseEntity<Object> tokenVerifcation(@RequestBody TokenVerficationForm tokenVerficationForm) {
        System.out.println("hello");
        String email = tokenVerficationForm.getEmail();
        String token = tokenVerficationForm.getToken();
        if (Objects.isNull(email) && Objects.isNull(token)) {
            return ResponseHandler.response(HttpStatus.BAD_REQUEST, true, "object are null");
        }
        AuthenticationToken authenticationToken = authenticationService.findByToken(tokenVerficationForm.getToken());
//if(authenticationToken.isDeleted()){
//    return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"token is deleted");
//}
        User user = userRepository.findByEmail(tokenVerficationForm.getEmail());
        if (user.isUserRegistered() && user.isEmailVerified()) {
            return ResponseHandler.response(HttpStatus.BAD_REQUEST, true, "user is already registered");

        }
        String savedToken = authenticationToken.getToken();
        if (savedToken.equalsIgnoreCase(token)) {
            user.setEmailVerified(true);
            user.setUserRegistered(true);
            user.setVerified(true);
            authenticationToken.setVerified(true);
            authenticationToken.setDeleted(true);

            User savedUser = userService.saveUser(user);
            String message = "token verfication success";
            String subject = "token verification";
            mailService.mailSend(email, subject, message);
            return ResponseHandler.response(HttpStatus.ACCEPTED, false, "verification success", user);


        }
        return ResponseHandler.response(HttpStatus.BAD_REQUEST, true, "verification fail");

    }

    @PostMapping("/userLogin")
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm) {
        User user = null;
        user = userService.findByEmail(loginForm.getEmail());
        if (Objects.isNull(user) || loginForm.getPassword() == null || loginForm.getPassword().isEmpty()) {
            return ResponseHandler.response(HttpStatus.BAD_REQUEST, true, "invalid.credential");
        }

        if (!user.isUserRegistered()) {
            return ResponseHandler.response(HttpStatus.BAD_REQUEST, true, "user is not registered");

        }
        if (user.isVerified()) {
            if (user.getPassword().equals(loginForm.getPassword())) {
                user.setLoggedIn(true);
                String token = TokenGenerator.generateToken();
                Map<String, Object> map = new TreeMap<>();
                map.put("user", user);
                map.put("token", token);
                AuthenticationToken authenticationToken = new AuthenticationToken(token, user);
                authenticationToken.setToken(token);

                authenticationTokenrepo.save(authenticationToken);
                userRepository.save(user);
                return ResponseHandler.response(HttpStatus.OK, false, "user login successfully", map);

            }

        }
        return ResponseHandler.response(HttpStatus.BAD_REQUEST, true, "something went wrong");
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Object> logout(@RequestParam String authToken) {
        if (authToken.isEmpty()) {
            return ResponseHandler.response(HttpStatus.BAD_REQUEST, true, "invalid token");
        }
        AuthenticationToken authenticationToken = authenticationTokenrepo.findByToken(authToken);
        if (Objects.isNull(authenticationToken)) {
            ResponseHandler.response(HttpStatus.NOT_FOUND, true, "invalid token");

        }
        User user = authenticationToken.getUser();

        AuthenticationResponse authenticationResponse = authenticationService.logout(authToken, user);
        if (authenticationResponse.isStatus()) {
            return ResponseHandler.response(HttpStatus.OK, false, "logout successfully", authenticationResponse);
        }
        return ResponseHandler.response(HttpStatus.BAD_REQUEST, true, "user invalid");


        // AuthenticationResponse response=
    }
    @PostMapping("/sendCodeForForgetPassword")
    public ResponseEntity<Object> sendCodeForFogetPasswordEmail(@RequestBody ForgetPasswordForm forgetPasswordform){
        Map<String,Object> response=new HashMap<>();
        if(forgetPasswordform.getEmail().isEmpty()){
            return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"invalid email");
        }
        User isuserExist=userService.findByEmail(forgetPasswordform.getEmail().trim());
        if(Objects.isNull(isuserExist)){
            return ResponseHandler.response(HttpStatus.NOT_FOUND,true,"user not found");
        }
        else {
            Otp otp = userService.sendOtpOnEmail(forgetPasswordform.getEmail());
            AuthenticationToken authenticationToken = userService.createAuthenticationToken(isuserExist);
            response.put("token", authenticationToken);
            response.put("email", otp.getEmail());
            return ResponseHandler.response(HttpStatus.OK, false, "otp send successfully", response);
        }
    }

    @PutMapping("/verifyForgetPasswordOtp")
    public ResponseEntity<Object> verifyForgetPasswordOtp(@RequestBody VerifyForgetPasswordForm verifyForgetPasswordForm){
        AuthenticationToken authenticationToken=authenticationTokenrepo.findByTokenAndIsDeleted(verifyForgetPasswordForm.getToken(),false);
        if(Objects.isNull(authenticationToken)){
            ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"invalid token");}
            User user=authenticationToken.getUser();
            if(Objects.isNull(user)){
                return ResponseHandler.response(HttpStatus.NOT_FOUND,true,"user not found");
            }
            if(!user.isUserRegistered()){
                return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"user is not registered");
            }
            Otp otpObj=otpService.findByOTPNumber(Integer.valueOf(verifyForgetPasswordForm.getOtp()));
            if(Objects.isNull(otpObj)){
                return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"otp object is null");

            }
            if(otpObj.isVerfied()){
                otpService.deleteOtpObject(otpObj);
                return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"otp already verified");

            }
            Otp responseOfOtp=otpService.setIsVerified(otpObj);
            if(Objects.isNull(responseOfOtp)){
                return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"otp verfication failure");
            }
            otpService.saveOtp(responseOfOtp);
            return  ResponseHandler.response(HttpStatus.ACCEPTED,false,"forget password otp verification success");
        }
        @PutMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordForm resetPasswordForm){
        AuthenticationToken authenticationToken=authenticationTokenrepo.findByTokenAndIsDeleted(resetPasswordForm.getToken(),false);
        if(Objects.isNull(authenticationToken)){
            return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"token Invalid");
        }
        User user=authenticationToken.getUser();
        if(Objects.isNull(user)){
            return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"user  not found");
        }
        if(!user.isUserRegistered()){
            return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"user not registered");
        }
User savedResponseOfUser=authenticationService.resetUserPassword(resetPasswordForm,authenticationToken,user);
  Map<String,Object> map=new HashMap<>();
  map.put("Response Object",savedResponseOfUser);
  if(!Objects.isNull(savedResponseOfUser)){
      return ResponseHandler.response(HttpStatus.OK,false,"user password change successfully");

  }
  return ResponseHandler.response(HttpStatus.BAD_REQUEST,true,"something went wrong");

        }

    }


