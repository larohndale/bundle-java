/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.authentication;

import com.akveo.bundlejava.role.Role;
import com.akveo.bundlejava.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class TokenService {
    @Value("${jwt.accessTokenSecretKey}")
    private String accessTokenSecretKey;

    @Value("${jwt.refreshTokenSecretKey}")
    private String refreshTokenSecretKey;

    @Value("${jwt.accessTokenValidityInMilliseconds}")
    private long accessTokenValidityInMilliseconds;

    @Value("${jwt.refreshTokenValidityInMilliseconds}")
    private long refreshTokenValidityInMilliseconds;

    private UserDetailsService userDetailsService;

    @Autowired
    public TokenService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        accessTokenSecretKey = Base64.getEncoder().encodeToString(accessTokenSecretKey.getBytes(UTF_8));
    }

    Token createToken(User user) {
        Token token = new Token();
        long expiresIn = expiration(accessTokenValidityInMilliseconds);

        token.setAccessToken(createAccessToken(user));
        token.setRefreshToken(createRefreshToken(user));
        token.setExpiresIn(expiresIn);

        return token;
    }

    Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmailFromAccessToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    String getEmailFromAccessToken(String token) throws JwtException {
        return Jwts.parser().setSigningKey(accessTokenSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    String getEmailFromRefreshToken(String token) throws JwtException {
        return Jwts.parser().setSigningKey(refreshTokenSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    String resolveToken(HttpServletRequest req) {
        String bearer = "Bearer ";
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith(bearer)) {
            return bearerToken.substring(bearer.length());
        }
        return null;
    }

    boolean isValid(String token) throws Exception {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(accessTokenSecretKey)
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new Exception("Expired or invalid JWT token");
        }
    }

    private String createAccessToken(User user) {
        long expiresIn = expiration(accessTokenValidityInMilliseconds);

        return createToken(user, expiresIn, accessTokenSecretKey);
    }

    private String createRefreshToken(User user) {
        long expiresIn = expiration(refreshTokenValidityInMilliseconds);

        return createToken(user, expiresIn, refreshTokenSecretKey);
    }

    private List<String> getRoleNames(Set<Role> roles) {
        List<String> roleNames = new ArrayList<>();
        for (Role role : roles) {
            roleNames.add(role.getName().toLowerCase());
        }
        return roleNames;
    }

    private String createToken(User user, long expiresIn, String key) {
        Claims claims = Jwts.claims();

        claims.setSubject(user.getEmail());
        claims.put("fullName", String.join(" ", user.getFirstName(), user.getLastName()));
        claims.put("createdAt", user.getCreatedAt());
        claims.put("role", getRoleNames(user.getRoles()));

        Date now = new Date();
        Date expirationDate = new Date(expiresIn);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    private long expiration(long validity) {
        Date now = new Date();
        return now.getTime() + validity;
    }
}
