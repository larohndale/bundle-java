package com.akveo.bundlejava.authentication.resetpassword;

import com.akveo.bundlejava.authentication.resetpassword.exception.TokenNotFoundOrExpiredHttpException;
import com.akveo.bundlejava.user.ChangePasswordRequest;
import com.akveo.bundlejava.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RestorePasswordService {
    @Autowired
    private RestorePasswordTokenRepository restorePasswordTokenRepository;

    @Autowired
    private UserService userService;

    public void resetPassword(RestorePasswordRequest restorePasswordRequest) {
        RestorePasswordToken restorePasswordToken =
                restorePasswordTokenRepository.findByToken(restorePasswordRequest.getToken());

        if (Objects.isNull(restorePasswordToken) || restorePasswordToken.isExpired()) {
            throw new TokenNotFoundOrExpiredHttpException();
        }

        changePassword(restorePasswordRequest, restorePasswordToken);
        restorePasswordTokenRepository.delete(restorePasswordToken);
    }

    private void changePassword(RestorePasswordRequest restorePasswordRequest,
                                RestorePasswordToken restorePasswordToken) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setUser(restorePasswordToken.getUser());
        changePasswordRequest.setPassword(restorePasswordRequest.getPassword());

        userService.changePassword(changePasswordRequest);
    }
}
