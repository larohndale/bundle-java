/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.user.exception;

import com.akveo.bundlejava.exception.HttpException;
import org.springframework.http.HttpStatus;

public class UserInvalidHttpException extends HttpException {

    private static final long serialVersionUID = 2401650728998512026L;

    public UserInvalidHttpException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
