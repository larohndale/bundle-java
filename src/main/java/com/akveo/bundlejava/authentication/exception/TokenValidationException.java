package com.akveo.bundlejava.authentication.exception;

public class TokenValidationException extends Exception {

    public TokenValidationException(String message) {
        super(message);
    }

    public TokenValidationException() {
    }
}
