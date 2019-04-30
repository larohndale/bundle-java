/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.authentication.resetpassword;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class RestorePasswordJob {

    @Autowired
    private RestorePasswordService restorePasswordService;

    private static final Logger logger = LoggerFactory.getLogger(RestorePasswordJob.class);

    @Scheduled(cron = "${client.resetPasswordToken.clearJob}")
    public void reportCurrentTime() {
        logger.info("Clear expired restore tokens");
        restorePasswordService.removeExpiredRestorePasswordTokens();
    }

}
