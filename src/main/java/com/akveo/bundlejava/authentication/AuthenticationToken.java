package com.akveo.bundlejava.authentication;

import com.akveo.bundlejava.authentication.exception.TokenValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class AuthenticationToken {
    private String token;

    private Properties properties;

    private Jws<Claims> claims;

    public AuthenticationToken(String token, Properties properties) throws TokenValidationException {
        this.token = token;
        this.properties = properties;
        try {
            claims = Jwts.parser()
                    .setSigningKey(properties.getAccessTokenSecretKey())
                    .parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenValidationException("Expired or invalid JWT token");
        }
    }

    public boolean isExpired() {
        return claims.getBody().getExpiration().before(new Date());
    }

    //package private
    String getEmailFromAccessToken() throws JwtException {
        return Jwts.parser().setSigningKey(properties.getAccessTokenSecretKey()).
                parseClaimsJws(token).getBody().getSubject();
    }

}
