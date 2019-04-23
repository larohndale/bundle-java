package com.akveo.bundlejava.authentication;


import com.akveo.bundlejava.email.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginRequest {
    @ValidEmail
    @NotEmpty
    @NotNull
    private String email;

    @NotNull
    @NotEmpty
    private String password;

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
}
