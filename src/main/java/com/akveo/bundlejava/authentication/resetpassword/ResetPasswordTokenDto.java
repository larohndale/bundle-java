package com.akveo.bundlejava.authentication.resetpassword;

import java.time.LocalDateTime;

public class ResetPasswordTokenDto {
    private String token;

    private LocalDateTime expiryDate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
