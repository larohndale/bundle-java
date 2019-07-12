package com.akveo.bundlejava.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class Properties {

    @Value("${jwt.accessTokenSecretKey}")
    private String accessTokenSecretKey;

    @Value("${jwt.refreshTokenSecretKey}")
    private String refreshTokenSecretKey;

    @Value("${jwt.accessTokenValidityInMilliseconds}")
    private long accessTokenValidityInMilliseconds;

    @Value("${jwt.refreshTokenValidityInMilliseconds}")
    private long refreshTokenValidityInMilliseconds;

    @PostConstruct
    protected void init() {
        accessTokenSecretKey = Base64
                .getEncoder()
                .encodeToString(accessTokenSecretKey
                        .getBytes(UTF_8));
    }

    public String getAccessTokenSecretKey() {
        return accessTokenSecretKey;
    }

    public String getRefreshTokenSecretKey() {
        return refreshTokenSecretKey;
    }

    public long getAccessTokenValidityInMilliseconds() {
        return accessTokenValidityInMilliseconds;
    }

    public long getRefreshTokenValidityInMilliseconds() {
        return refreshTokenValidityInMilliseconds;
    }
}
