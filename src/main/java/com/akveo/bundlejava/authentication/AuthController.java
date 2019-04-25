package com.akveo.bundlejava.authentication;

import com.akveo.bundlejava.authentication.resetpassword.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private RestorePasswordService restorePasswordService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
        Token token = authService.login(loginRequest);
        return toResponse(token);
    }

    @PostMapping("/restore-pass")
    public ResponseEntity resetPassword(@Valid @RequestBody RestorePasswordRequest restorePasswordRequest) {
        restorePasswordService.resetPassword(restorePasswordRequest);
        return ok(null);
    }

    @PostMapping("/sign-up")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest) {
        Token token = authService.register(registerRequest);
        return toResponse(token);
    }

    @PostMapping("/request-pass")
    public ResponseEntity forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        forgotPasswordService.forgotPassword(forgotPasswordRequest);
        return ok(null);
    }

    // TODO remove as soon as Nebular 3.3.0 released
    @PostMapping("/sign-out")
    public ResponseEntity logout() {
        return ok(null);
    }

    @PostMapping("/reset-pass")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        resetPasswordService.resetPassword(resetPasswordRequest);
        return ok(null);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        Token token = authService.refreshToken(refreshTokenRequest);
        return toResponse(token);
    }

    private ResponseEntity toResponse(Token token) {
        Map<String, Token> model = Stream.of(
                new AbstractMap.SimpleEntry<>("token", token))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return ok(model);
    }
}
