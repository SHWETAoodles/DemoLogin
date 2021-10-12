package com.example.demo.service;

import com.example.demo.dto.ForgetPasswordForm;
import com.example.demo.dto.LoginDetails;
import com.example.demo.dto.ResetPasswordForm;
import com.example.demo.model.AuthenticationToken;
import com.example.demo.model.Otp;
import com.example.demo.model.User;
import com.example.demo.repository.AuthenticationRepository;
import com.example.demo.repository.LoginDetailsRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utilPackage.AuthenticationResponse;
import com.example.demo.utilPackage.ResponseHandler;
import com.example.demo.utilPackage.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    @Autowired
    AuthenticationRepository authenticationRepository;
    @Autowired
    JWTservice jwTservice;
    @Autowired
    LoginDetailsRepository loginRepo;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationService authenticationService;


    public AuthenticationToken findByToken(String token) {
        return authenticationRepository.findByToken(token);
    }

    @Override
    public AuthenticationResponse logout(String authToken, User user) {
        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        if(Objects.isNull(user)||StringUtils.isEmpty(authToken)){
            authenticationResponse.setStatus(false);
            authenticationResponse.setMessage("invalid user");

        }

        List<LoginDetails> loginDetails=loginRepo.findByUserAndIsDeleted(user,false);
        if(loginDetails.isEmpty()){
            authenticationResponse.setStatus(false);
            authenticationResponse.setMessage("user is not login");
            return authenticationResponse;
        }else {
            loginDetails.stream().forEach(loginDetail -> {
                loginDetail.setDeleted(true);
                loginRepo.save(loginDetail);
            });
            AuthenticationToken authenticationToken=authenticationRepository.findByToken(authToken);
            user.setLoggedIn(false);
            userRepository.save(user);

            authenticationToken.setDeleted(true);
            authenticationRepository.saveAndFlush(authenticationToken);
            authenticationResponse.setStatus(true);
            authenticationResponse.setMessage("logout successfully");
            return authenticationResponse;
        }

    }

    @Override
    public AuthenticationToken login(String password, User user) {

        AuthenticationToken authToken=userService.createAuthenticationToken(user);
        //authToken=jwTservice.generateAuthToken();
        Session session=new Session();
        session.setAuthenticationToken(authToken);
        session.setUser(user);
        LoginDetails loginDetails=new LoginDetails();
        loginDetails.setDeleted(false);
        loginDetails.setUser(user);
        loginRepo.save(loginDetails);
        return authToken;


    }

    @Override
    public User resetUserPassword(ResetPasswordForm resetPasswordForm, AuthenticationToken authenticationToken, User user) {
        user.setPassword(resetPasswordForm.getNewPassword());
        authenticationToken.setDeleted(true);
        authenticationRepository.save(authenticationToken);
        userRepository.save(user);
        return user;

    }

}
