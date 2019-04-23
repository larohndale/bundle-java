package com.akveo.bundlejava.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {
    private TokenService tokenService;

    JwtTokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        String token = tokenService.resolveToken((HttpServletRequest) req);

        try {
            if (token == null || !tokenService.isValid(token)) {
                filterChain.doFilter(req, res);
                return;
            }

            Authentication auth = tokenService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(req, res);
    }
}