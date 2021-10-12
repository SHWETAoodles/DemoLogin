package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@JsonIgnoreProperties
public class LoginForm {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @Email
    private String email;
    @NotEmpty
    private String password;

}
