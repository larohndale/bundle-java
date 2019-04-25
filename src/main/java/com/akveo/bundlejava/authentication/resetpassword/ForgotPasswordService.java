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
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    private Logger logger = LoggerFactory.getLogger(ForgotPasswordService.class);


    @Autowired
    private UserService userService;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

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
        ResetPasswordToken token = new ResetPasswordToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiresIn(this.calculateExpirationDate(resetPasswordTokenExpiration));
        resetPasswordTokenRepository.save(token);

        // send reset password token via email
        try {
            String resetPasswordUrl = createResetUrl(token);
            // Reset Password Token should be sent via Email. You can use reset url in your template
            logger.info("Reset url was created: {}", resetPasswordUrl);
        } catch (JsonProcessingException exception) {
            throw new CantSendEmailHttpException();
        }
    }

    private Date calculateExpirationDate(Duration tokenExpirationDuration) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, (int) tokenExpirationDuration.toMinutes());
        return now.getTime();
    }

    private String createResetUrl(ResetPasswordToken resetPasswordToken) throws JsonProcessingException {
        // create reset password token dto
        ResetPasswordTokenDto resetPasswordTokenDto = new ResetPasswordTokenDto();
        resetPasswordTokenDto.setExpiryDate(resetPasswordToken.getExpiresIn());
        resetPasswordTokenDto.setToken(resetPasswordToken.getToken());

        // map token dto to json
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonToken = objectMapper.writeValueAsString(resetPasswordTokenDto);

        // encode with base64
        String encodedToken = Base64.getEncoder().encodeToString(jsonToken.getBytes());

        return clientUrl + "/auth/reset-pass?token=" + encodedToken;
    }
}
