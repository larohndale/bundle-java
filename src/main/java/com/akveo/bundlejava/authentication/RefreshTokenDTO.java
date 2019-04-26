package com.akveo.bundlejava.authentication;

import javax.validation.constraints.NotNull;

public class RefreshTokenDTO {
    @NotNull
    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
