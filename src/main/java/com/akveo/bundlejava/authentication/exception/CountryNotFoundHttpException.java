package com.akveo.bundlejava.authentication.exception;

import com.akveo.bundlejava.exception.HttpException;
import org.springframework.http.HttpStatus;

public class CountryNotFoundHttpException extends HttpException {
    private static final long serialVersionUID = 4770986620665158856L;

    public CountryNotFoundHttpException(String message, HttpStatus status) {
        super(message, status);
    }
}