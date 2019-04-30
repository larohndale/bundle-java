/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.authentication.resetpassword.exception;

import com.akveo.bundlejava.exception.HttpException;
import org.springframework.http.HttpStatus;

public class IncorrectEmailHttpException extends HttpException {
    public IncorrectEmailHttpException() {
        // TODO check http status
        super("Email is invalid or doesn't registered", HttpStatus.FORBIDDEN);
    }
}
