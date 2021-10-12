package com.example.demo.dto;

import com.example.demo.model.User;

import javax.persistence.*;

@Entity
public class LoginDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String city;
    private String country;
    @ManyToOne
    private User user;

    public LoginDetails() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    private boolean isDeleted;

}
