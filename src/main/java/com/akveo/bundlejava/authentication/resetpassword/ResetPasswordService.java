package com.akveo.bundlejava.authentication.resetpassword;

import com.akveo.bundlejava.authentication.resetpassword.exception.TokenNotFoundOrExpiredHttpException;
import com.akveo.bundlejava.user.ChangePasswordRequest;
import com.akveo.bundlejava.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ResetPasswordService {
    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    private UserService userService;

    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        ResetPasswordToken ResetPasswordToken =
                resetPasswordTokenRepository.findByToken(resetPasswordRequest.getToken());

        if (Objects.isNull(ResetPasswordToken) || ResetPasswordToken.isExpired()) {
            throw new TokenNotFoundOrExpiredHttpException();
        }

        changePassword(resetPasswordRequest, ResetPasswordToken);
        resetPasswordTokenRepository.delete(ResetPasswordToken);
    }

    private void changePassword(ResetPasswordRequest resetPasswordRequest,
                                ResetPasswordToken ResetPasswordToken) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setUser(ResetPasswordToken.getUser());
        changePasswordRequest.setPassword(resetPasswordRequest.getPassword());

        userService.changePassword(changePasswordRequest);
    }
}
