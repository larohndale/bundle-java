package com.akveo.bundlejava.ecommerce.repository;

import com.akveo.bundlejava.ecommerce.entity.AggregatedData;
import com.akveo.bundlejava.ecommerce.entity.Country;
import com.akveo.bundlejava.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface OrderAggregatedRepository extends JpaRepository<Order, Long> {
}
