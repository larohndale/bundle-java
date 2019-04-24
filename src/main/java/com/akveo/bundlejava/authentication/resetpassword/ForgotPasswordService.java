package com.akveo.bundlejava.authentication.resetpassword;

import com.akveo.bundlejava.authentication.resetpassword.exception.CantSendEmailHttpException;
import com.akveo.bundlejava.authentication.resetpassword.exception.IncorrectEmailHttpException;
import com.akveo.bundlejava.mailing.SendMailException;
import com.akveo.bundlejava.user.User;
import com.akveo.bundlejava.user.UserService;
import com.akveo.bundlejava.user.exception.UserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserService userService;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    private ResetPasswordEmailService resetPasswordEmailService;

//    @Value("${client.url}")
//    private String clientUrl;

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
        // token will expire in an hour
        token.setExpiryDate(60);
        resetPasswordTokenRepository.save(token);

        // send reset password token via email
//        try {
//            String resetPasswordUrl = createResetUrl(token);
//            resetPasswordEmailService.send(user, resetPasswordUrl);
//        } catch (SendMailException | JsonProcessingException exception) {
//            throw new CantSendEmailHttpException();
//        }
    }

//    private String createResetUrl(ResetPasswordToken ResetPasswordToken) throws JsonProcessingException {
//        // create reset password token dto
//        ResetPasswordTokenDto resetPasswordTokenDto = new ResetPasswordTokenDto();
//        resetPasswordTokenDto.setExpiryDate(ResetPasswordToken.getExpiresIn());
//        resetPasswordTokenDto.setToken(ResetPasswordToken.getToken());
//
//        // map token dto to json
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonToken = objectMapper.writeValueAsString(resetPasswordTokenDto);
//
//        // encode with base64
//        String encodedToken = Base64.getEncoder().encodeToString(jsonToken.getBytes());
//
//        return clientUrl + "/auth/reset-password?token=" + encodedToken;
//    }
}
