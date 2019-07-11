package com.akveo.bundlejava.authentication;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class TokenValidationService {

    private String accessTokenSecretKey;

    @PostConstruct
    protected void init() {
        Properties prop = new Properties();
        accessTokenSecretKey = prop.getAccessTokenSecretKey();
        accessTokenSecretKey = Base64
                .getEncoder()
                .encodeToString(accessTokenSecretKey
                        .getBytes(UTF_8));
    }

    public boolean isValid(String token) throws Exception {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(accessTokenSecretKey)
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new Exception("Expired or invalid JWT token");
        }
    }
}
