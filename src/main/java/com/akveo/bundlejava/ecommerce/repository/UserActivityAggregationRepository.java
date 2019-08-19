package com.akveo.bundlejava.ecommerce.repository;

import com.akveo.bundlejava.ecommerce.entity.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityAggregationRepository extends JpaRepository<UserActivity, Long> {
}
