package com.example.demo.dto;

public class TokenVerficationForm {
    private String email;
    private String token;

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "TokenVerficationForm{" +
                "email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
