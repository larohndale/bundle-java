package com.akveo.bundlejava.authentication.exception;

import com.akveo.bundlejava.exception.HttpException;
import org.springframework.http.HttpStatus;

public class UserNotFoundHttpException extends HttpException {
    private static final long serialVersionUID = 4770986620665158856L;

    public UserNotFoundHttpException() {
        super("Incorrect email or password", HttpStatus.FORBIDDEN);
    }
}
