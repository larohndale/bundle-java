/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.authentication.resetpassword;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class RestorePasswordJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestorePasswordJob.class);

    private RestorePasswordService restorePasswordService;

    @Autowired
    public RestorePasswordJob(RestorePasswordService restorePasswordService) {
        this.restorePasswordService = restorePasswordService;
    }

    @Scheduled(cron = "${client.resetPasswordToken.clearJob}")
    public void reportCurrentTime() {
        LOGGER.info("Clear expired restore tokens");
        restorePasswordService.removeExpiredRestorePasswordTokens();
    }

}
