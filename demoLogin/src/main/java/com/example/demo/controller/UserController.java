package com.example.demo.controller;

import com.example.demo.constant.MessageConstant;
import com.example.demo.model.AuthenticationToken;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MailService;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceInterface;
import com.example.demo.utilPackage.LocalService;
import com.example.demo.utilPackage.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserServiceInterface userServiceInterface;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;
    @Autowired
    LocalService localService;

    @Autowired
    UserRepository userRepository;


@PostMapping("/UserRegister")
    public ResponseEntity<Object> registerUser( @RequestBody User user){
    ResponseEntity<Object> registerUser=userServiceInterface.registerUser(user);
    //ResponseEntity<Object> registerUser=userServiceInterface.registerUser(user);
    AuthenticationToken savedAuthToken = userServiceInterface.createAuthenticationToken(user);
    System.out.println(savedAuthToken.getToken());

userService.sendMailForLinkVerification(user,savedAuthToken );

return ResponseHandler.response(HttpStatus.CREATED,false,"user registered success",registerUser);

//    return new ResponseEntity<Object>(registerUser, HttpStatus.CREATED);


}

//@PostMapping("/sendmail")
//    public ResponseEntity<String> sendMail(@RequestBody Mail mail){
//    mailService.mailSend("shwetasingh4249@gmal.com","hello","welcome");
//    return new ResponseEntity<String>("mail send",HttpStatus.CREATED);
//
//}

    @GetMapping("/hello")
    public String hello(){
        return "hello user";
    }
    @GetMapping("/{userId}")
    public Optional<User> getUserById(@PathVariable Long userId){
    return userRepository.findById(userId);
    }

}
