package com.akveo.bundlejava.authentication.resetpassword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {

    ResetPasswordToken findByToken(String token);
}
