package com.example.demo.dto;

import com.sun.istack.NotNull;

import javax.validation.constraints.Email;

public class ForgetPasswordForm {
    @NotNull
@Email(message = "email should not be empty")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
