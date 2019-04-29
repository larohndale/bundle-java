package com.akveo.bundlejava.user.exception;


import com.akveo.bundlejava.exception.HttpException;
import org.springframework.http.HttpStatus;

public class UserInvalidHttpException extends HttpException {

    private static final long serialVersionUID = 2401650728998512026L;

    public UserInvalidHttpException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
