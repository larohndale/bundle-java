package com.akveo.bundlejava.ecommerce.repository;

import com.akveo.bundlejava.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAggregatedRepository extends JpaRepository<Order, Long> {
}
