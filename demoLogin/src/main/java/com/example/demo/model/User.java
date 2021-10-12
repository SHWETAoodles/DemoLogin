package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name="LoginDemo")
public class User extends Object {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public boolean isUserRegistered() {
        return isUserRegistered;
    }

    public void setUserRegistered(boolean userRegistered) {
        isUserRegistered = userRegistered;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    private String name;
    private boolean isUserRegistered;
    private boolean isEmailVerified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    private String password;

    public User(Long id, String name, boolean isUserRegistered, boolean isEmailVerified, String password, String address, String email, String designation) {
        this.id = id;
        this.name = name;
        this.isUserRegistered = isUserRegistered;
        this.isEmailVerified = isEmailVerified;
        this.password = password;
        this.address = address;
        this.email = email;
        this.designation = designation;
    }

    private String address;

    public User() {
    }

    private String email;
    private String designation;

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public boolean loggedIn;
    public boolean isVerified;
}
