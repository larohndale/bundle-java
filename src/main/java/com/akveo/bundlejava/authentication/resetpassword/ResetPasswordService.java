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

    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        if (!resetPasswordRequest.getConfirmPassword().equals(resetPasswordRequest.getNewPassword())) {
            throw new PasswordsDontMatchException();
        }

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setUser(UserContextHolder.getUser());
        changePasswordRequest.setPassword(resetPasswordRequest.getNewPassword());
        userService.changePassword(changePasswordRequest);
    }

}
