package com.example.demo.service;

import com.example.demo.dto.ResetPasswordForm;
import com.example.demo.model.AuthenticationToken;
import com.example.demo.model.User;
import com.example.demo.utilPackage.AuthenticationResponse;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    AuthenticationToken findByToken(String token);
    AuthenticationResponse logout(String authToken, User user);
    AuthenticationToken login(String password, User user );
    User resetUserPassword(ResetPasswordForm resetPasswordForm,AuthenticationToken authenticationToken,User user);
}
