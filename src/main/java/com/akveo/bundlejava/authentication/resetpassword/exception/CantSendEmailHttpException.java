package com.akveo.bundlejava.authentication.resetpassword.exception;

import com.akveo.bundlejava.exception.HttpException;
import org.springframework.http.HttpStatus;

public class CantSendEmailHttpException extends HttpException {
    public CantSendEmailHttpException() {
        super("Can't reset password, please, try again later", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
