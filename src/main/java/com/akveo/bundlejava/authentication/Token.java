/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.authentication;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Token {
    private Long expiresIn;

    private String accessToken;

    private String refreshToken;

    @JsonGetter("expires_in")
    public Long getExpiresIn() {
        return expiresIn;
    }

    @JsonSetter("expires_in")
    public void setExpiresIn(Long expires_in) {
        this.expiresIn = expires_in;
    }

    @JsonGetter("access_token")
    public String getAccessToken() {
        return accessToken;
    }

    @JsonSetter("access_token")
    public void setAccessToken(String access_token) {
        this.accessToken = access_token;
    }

    @JsonGetter("refresh_token")
    public String getRefreshToken() {
        return refreshToken;
    }

    @JsonSetter("refresh_token")
    public void setRefreshToken(String refresh_token) {
        this.refreshToken = refresh_token;
    }
}
