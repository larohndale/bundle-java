/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.authentication.resetpassword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
public interface RestorePasswordTokenRepository extends JpaRepository<RestorePassword, Long> {

    RestorePassword findByToken(String token);

    @Transactional
    @Modifying
    @Query("delete from RestorePassword rp where rp.expiresIn < :localDateTime")
    void deleteExpiredRestorePasswordTokens(LocalDateTime localDateTime);
}
