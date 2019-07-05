/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.user.exception;

import com.akveo.bundlejava.exception.ApiException;

public class UserNotFoundException extends ApiException {
    private static final long serialVersionUID = -5258959358527382145L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
