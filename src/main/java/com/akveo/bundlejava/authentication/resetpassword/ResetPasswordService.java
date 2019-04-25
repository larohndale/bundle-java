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
        ResetPasswordToken resetPasswordToken =
                resetPasswordTokenRepository.findByToken(resetPasswordRequest.getToken());

        if (Objects.isNull(resetPasswordToken) || resetPasswordToken.isExpired()) {
            throw new TokenNotFoundOrExpiredHttpException();
        }

        changePassword(resetPasswordRequest, resetPasswordToken);
        resetPasswordTokenRepository.delete(resetPasswordToken);
    }

    private void changePassword(ResetPasswordRequest resetPasswordRequest,
                                ResetPasswordToken resetPasswordToken) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setUser(resetPasswordToken.getUser());
        changePasswordRequest.setPassword(resetPasswordRequest.getPassword());

        userService.changePassword(changePasswordRequest);
    }
}
