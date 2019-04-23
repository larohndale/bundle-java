package com.akveo.bundlejava.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
