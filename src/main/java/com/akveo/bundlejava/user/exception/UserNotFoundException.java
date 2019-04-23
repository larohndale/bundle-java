package com.akveo.bundlejava.user.exception;


import com.akveo.bundlejava.exception.ApiException;

public class UserNotFoundException extends ApiException {
    private static final long serialVersionUID = -5258959358527382145L;

    public UserNotFoundException(String email) {
        super("User with email: " + email + " not found");
    }
}
