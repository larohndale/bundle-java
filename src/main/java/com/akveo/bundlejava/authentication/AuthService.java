package com.akveo.bundlejava.authentication;

import com.akveo.bundlejava.authentication.exception.InvalidTokenHttpException;
import com.akveo.bundlejava.authentication.exception.UserAlreadyExistsHttpException;
import com.akveo.bundlejava.authentication.exception.UserNotFoundHttpException;
import com.akveo.bundlejava.mailing.SendMailException;
import com.akveo.bundlejava.user.User;
import com.akveo.bundlejava.user.UserService;
import com.akveo.bundlejava.user.exception.UserAlreadyExistsException;
import com.akveo.bundlejava.user.exception.UserNotFoundException;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WelcomeEmailService welcomeEmailService;

    Token register(RegisterRequest registerRequest) throws UserAlreadyExistsHttpException {
        try {
            User user = userService.register(registerRequest);
            sendWelcomeMail(user);
            return createToken(user);
        } catch (UserAlreadyExistsException exception) {
            throw new UserAlreadyExistsHttpException();
        }
    }

    Token login(LoginRequest loginRequest) throws UserNotFoundHttpException {
        try {
            Authentication authentication = createAuthentication(loginRequest);
            BundleUserDetailsService.BundleUserDetails userDetails =
                    (BundleUserDetailsService.BundleUserDetails) authenticationManager.authenticate(authentication).getPrincipal();
            User user = userDetails.getUser();
            return createToken(user);
        } catch (AuthenticationException exception) {
            throw new UserNotFoundHttpException();
        }
    }

    Token refreshToken(RefreshTokenRequest refreshTokenRequest) throws InvalidTokenHttpException {
        try {
            String email = tokenService.getEmailFromRefreshToken(refreshTokenRequest.getToken().getRefreshToken());
            User user = userService.findByEmail(email);
            return createToken(user);
        } catch (JwtException | UserNotFoundException e) {
            throw new InvalidTokenHttpException();
        }
    }

    private Authentication createAuthentication(LoginRequest loginRequest) {
        return new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
    }

    private Token createToken(User user) {
        return tokenService.createToken(user);
    }

    private void sendWelcomeMail(User user) {
        try {
            welcomeEmailService.send(user);
            // That exception has to be handled here and shouldn't be mapped to HTTP exception.
            // Because sending welcome email operation should never crash registration process.
        } catch (SendMailException exception) {
            logger.error(exception.getMessage(), exception);
        }
    }
}
