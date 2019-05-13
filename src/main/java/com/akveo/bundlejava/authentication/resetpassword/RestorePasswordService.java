/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.authentication.resetpassword;

import com.akveo.bundlejava.authentication.resetpassword.exception.TokenNotFoundOrExpiredHttpException;
import com.akveo.bundlejava.user.ChangePasswordRequest;
import com.akveo.bundlejava.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class RestorePasswordService {

    private RestorePasswordTokenRepository restorePasswordTokenRepository;
    private UserService userService;

    @Autowired
    public RestorePasswordService(RestorePasswordTokenRepository restorePasswordTokenRepository,
                                  UserService userService) {
        this.restorePasswordTokenRepository = restorePasswordTokenRepository;
        this.userService = userService;
    }

    public void restorePassword(RestorePasswordDTO restorePasswordDTO) {
        RestorePassword restorePassword =
                restorePasswordTokenRepository.findByToken(restorePasswordDTO.getToken());

        if (Objects.isNull(restorePassword) || restorePassword.isExpired()) {
            throw new TokenNotFoundOrExpiredHttpException();
        }

        changePassword(restorePasswordDTO, restorePassword);
        restorePasswordTokenRepository.delete(restorePassword);
    }

    public void removeExpiredRestorePasswordTokens() {
        restorePasswordTokenRepository.deleteExpiredRestorePasswordTokens(LocalDateTime.now());
    }

    private void changePassword(RestorePasswordDTO restorePasswordDTO,
                                RestorePassword restorePassword) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setUser(restorePassword.getUser());
        changePasswordRequest.setPassword(restorePasswordDTO.getNewPassword());

        userService.changePassword(changePasswordRequest);
    }

}
