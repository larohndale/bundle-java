package com.akveo.bundlejava.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class Properties {

    @Value("cyy4KhQAOWuj94LtM6Yvt$FGOQb8KBN6lIXmFFG7!Yv6K#ewWCnH#Q5IS2MhxKp&")
    private static String accessTokenSecretKey;

    @Value("${jwt.refreshTokenSecretKey}")
    private static String refreshTokenSecretKey;

    @Value("${jwt.accessTokenValidityInMilliseconds}")
    private static long accessTokenValidityInMilliseconds;

    @Value("${jwt.refreshTokenValidityInMilliseconds}")
    private static long refreshTokenValidityInMilliseconds;


    public static String getAccessTokenSecretKey() {
        return accessTokenSecretKey;
    }

    public static String getRefreshTokenSecretKey() {
        return refreshTokenSecretKey;
    }

    public static long getAccessTokenValidityInMilliseconds() {
        return accessTokenValidityInMilliseconds;
    }

    public static long getRefreshTokenValidityInMilliseconds() {
        return refreshTokenValidityInMilliseconds;
    }
}
