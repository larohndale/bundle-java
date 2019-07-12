package com.akveo.bundlejava.authentication;

import com.akveo.bundlejava.authentication.exception.TokenValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class TokenValidationService {

    private Properties properties;

    @Autowired
    public TokenValidationService(Properties properties) {
        this.properties = properties;
    }

    public boolean isValid(String token) throws TokenValidationException {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(properties.getAccessTokenSecretKey())
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenValidationException("Expired or invalid JWT token");
        }
    }
}
