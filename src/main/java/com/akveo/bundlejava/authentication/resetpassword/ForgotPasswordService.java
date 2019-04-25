package com.akveo.bundlejava.authentication.resetpassword;

import com.akveo.bundlejava.authentication.resetpassword.exception.CantSendEmailHttpException;
import com.akveo.bundlejava.authentication.resetpassword.exception.IncorrectEmailHttpException;
import com.akveo.bundlejava.user.User;
import com.akveo.bundlejava.user.UserService;
import com.akveo.bundlejava.user.exception.UserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    private Logger logger = LoggerFactory.getLogger(ForgotPasswordService.class);


    @Autowired
    private UserService userService;

    @Autowired
    private RestorePasswordTokenRepository restorePasswordTokenRepository;

    @Value("${client.url}")
    private String clientUrl;

    @Value("${client.resetPasswordTokenExpiration}")
    private Duration resetPasswordTokenExpiration;

    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        User user;

        try {
            user = userService.findByEmail(forgotPasswordRequest.getEmail());
        } catch (UserNotFoundException exception) {
            throw new IncorrectEmailHttpException();
        }

        // generate reset password token
        RestorePasswordToken token = new RestorePasswordToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiresIn(this.calculateExpirationDate(resetPasswordTokenExpiration));
        restorePasswordTokenRepository.save(token);

        // send reset password token via email
        try {
            String resetPasswordUrl = createResetUrl(token);
            // Reset Password Token should be sent via Email. You can use reset url in your template
            logger.info("Reset url was created: {}", resetPasswordUrl);
        } catch (JsonProcessingException exception) {
            throw new CantSendEmailHttpException();
        }
    }

    private LocalDateTime calculateExpirationDate(Duration tokenExpirationDuration) {
        return LocalDateTime.now().plusMinutes(tokenExpirationDuration.toMinutes());
    }

    private String createResetUrl(RestorePasswordToken restorePasswordToken) throws JsonProcessingException {
        // create reset password token dto
        RestorePasswordTokenDto restorePasswordTokenDto = new RestorePasswordTokenDto();
        restorePasswordTokenDto.setExpiryDate(restorePasswordToken.getExpiresIn());
        restorePasswordTokenDto.setToken(restorePasswordToken.getToken());

        // map token dto to json
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonToken = objectMapper.writeValueAsString(restorePasswordTokenDto);

        // encode with base64
        String encodedToken = Base64.getEncoder().encodeToString(jsonToken.getBytes());

        return clientUrl + "/auth/reset-pass?token=" + encodedToken;
    }
}
