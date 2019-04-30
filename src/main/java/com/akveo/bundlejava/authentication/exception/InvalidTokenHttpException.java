/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.authentication.exception;

import com.akveo.bundlejava.exception.HttpException;
import org.springframework.http.HttpStatus;

public class InvalidTokenHttpException extends HttpException {
    private static final long serialVersionUID = 773684525186809237L;

    public InvalidTokenHttpException() {
        super(HttpStatus.FORBIDDEN);
    }
}
