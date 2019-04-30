/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.authentication.resetpassword;

import com.akveo.bundlejava.authentication.exception.PasswordsDontMatchException;
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

    public void restorePassword(RestorePasswordDTO restorePasswordDTO) {
        if (!restorePasswordDTO.getNewPassword().equals(restorePasswordDTO.getConfirmPassword())) {
            throw new PasswordsDontMatchException();
        }

        RestorePassword restorePassword =
                restorePasswordTokenRepository.findByToken(restorePasswordDTO.getToken());

        if (Objects.isNull(restorePassword) || restorePassword.isExpired()) {
            throw new TokenNotFoundOrExpiredHttpException();
        }

        changePassword(restorePasswordDTO, restorePassword);
        restorePasswordTokenRepository.delete(restorePassword);
    }

    private void changePassword(RestorePasswordDTO restorePasswordDTO,
                                RestorePassword restorePassword) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setUser(restorePassword.getUser());
        changePasswordRequest.setPassword(restorePasswordDTO.getNewPassword());

        userService.changePassword(changePasswordRequest);
    }
}
