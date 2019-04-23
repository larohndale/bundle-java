package com.akveo.bundlejava.authentication.resetpassword.exception;

import com.akveo.bundlejava.exception.HttpException;
import org.springframework.http.HttpStatus;

public class TokenNotFoundOrExpiredHttpException extends HttpException {
    public TokenNotFoundOrExpiredHttpException() {
        super("Reset password request wasn't performed or already expired", HttpStatus.FORBIDDEN);
    }
}
