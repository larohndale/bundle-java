/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

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
    private RequestPasswordService requestPasswordService;

    @Autowired
    private RestorePasswordService restorePasswordService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginDTO loginDTO) {
        Token token = authService.login(loginDTO);
        return toResponse(token);
    }

    @PostMapping("/restore-pass")
    public ResponseEntity restorePassword(@Valid @RequestBody RestorePasswordDTO restorePasswordDTO) {
        restorePasswordService.restorePassword(restorePasswordDTO);
        return ok(null);
    }

    @PostMapping("/sign-up")
    public ResponseEntity register(@Valid @RequestBody SignUpDTO signUpDTO) {
        Token token = authService.register(signUpDTO);
        return toResponse(token);
    }

    @PostMapping("/request-pass")
    public ResponseEntity requestPassword(@Valid @RequestBody RequestPasswordDTO requestPasswordDTO) {
        requestPasswordService.requestPassword(requestPasswordDTO);
        return ok(null);
    }

    @PostMapping("/sign-out")
    public ResponseEntity logout() {
        return ok(null);
    }

    @PostMapping("/reset-pass")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        resetPasswordService.resetPassword(resetPasswordDTO);
        return ok(null);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO) {
        Token token = authService.refreshToken(refreshTokenDTO);
        return toResponse(token);
    }

    private ResponseEntity toResponse(Token token) {
        Map<String, Token> model = Stream.of(
                new AbstractMap.SimpleEntry<>("token", token))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return ok(model);
    }
}
