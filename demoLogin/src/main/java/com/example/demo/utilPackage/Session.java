package com.example.demo.utilPackage;

import com.example.demo.model.AuthenticationToken;
import com.example.demo.model.User;

public class Session {
    private AuthenticationToken authenticationToken;
    private User user;

    public AuthenticationToken getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(AuthenticationToken authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    private Boolean isDeleted = false;
}
