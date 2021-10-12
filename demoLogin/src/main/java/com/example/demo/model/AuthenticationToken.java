package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AuthenticationToken<date> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private Date createdOn=new Date();
    private boolean isVerified;

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    private boolean isDeleted;
    @ManyToOne
    private User user;

    public AuthenticationToken(String token, User user) {
        this.token = token;
        this.user= user;
    }

    public AuthenticationToken(Long id, String token, Date createdOn, boolean isDeleted, User user) {
        this.id = id;
        this.token = token;
        this.createdOn = createdOn;
        this.isDeleted = isDeleted;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public AuthenticationToken() {
    }
}
