package com.akveo.bundlejava.ecommerce.repository;


import com.akveo.bundlejava.ecommerce.entity.Order;
import com.akveo.bundlejava.ecommerce.entity.filter.OrderGridFilter;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    //List<Order> getFilteredListWithTotalCount(OrderGridFilter filter);

//    @Query("SELECT order FROM Order o where o.type = :type")
//    List<Order> findAllByType(@Param("type") long type);
}
