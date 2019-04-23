package com.akveo.bundlejava.authentication.resetpassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ResetPasswordRequest {
    @NotNull
    @NotEmpty
    private String token;

    @NotNull
    @NotEmpty
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
