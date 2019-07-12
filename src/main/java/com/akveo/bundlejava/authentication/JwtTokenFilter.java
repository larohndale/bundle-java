/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.authentication;

import com.akveo.bundlejava.authentication.exception.TokenValidationException;
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
    private TokenValidationService tokenValidationService;

    JwtTokenFilter(TokenService tokenService,
                   TokenValidationService tokenValidationService
    ) {
        this.tokenService = tokenService;
        this.tokenValidationService = tokenValidationService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        String token = tokenService.resolveToken((HttpServletRequest) req);

        try {
            if (token == null || !tokenValidationService.isValid(token)) {
                filterChain.doFilter(req, res);
                return;
            }

            Authentication auth = tokenService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (TokenValidationException e) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(req, res);
    }
}
