package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name="OTP_Table")
public class Otp  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private int otp;
    private boolean isDeleted;
    private String email;
private boolean isVerfied;


    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Otp(int otp,String email, User user) {

        this.email = email;
        this.user = user;
    }

    public boolean isVerfied() {
        return isVerfied;
    }

    public void setVerfied(boolean verfied) {
        isVerfied = verfied;
    }



    public Otp(Long id, int otp, boolean isDeleted, String email, User user) {
        Id = id;
        this.otp = otp;
        this.isDeleted = isDeleted;
        this.email = email;
        this.user = user;
    }

    public Otp() {
    }

    @ManyToOne
    private User user;
}
