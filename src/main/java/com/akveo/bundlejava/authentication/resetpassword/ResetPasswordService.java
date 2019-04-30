/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.authentication.resetpassword;

import com.akveo.bundlejava.authentication.exception.PasswordsDontMatchException;
import com.akveo.bundlejava.user.ChangePasswordRequest;
import com.akveo.bundlejava.user.UserContextHolder;
import com.akveo.bundlejava.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService {

    @Autowired
    private UserService userService;

    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        if (!resetPasswordDTO.getConfirmPassword().equals(resetPasswordDTO.getPassword())) {
            throw new PasswordsDontMatchException();
        }

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setUser(UserContextHolder.getUser());
        changePasswordRequest.setPassword(resetPasswordDTO.getPassword());
        userService.changePassword(changePasswordRequest);
    }

}
