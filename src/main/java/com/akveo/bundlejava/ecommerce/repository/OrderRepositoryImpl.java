package com.akveo.bundlejava.ecommerce.repository;

import com.akveo.bundlejava.ecommerce.entity.Order;
import com.akveo.bundlejava.ecommerce.entity.filter.OrderGridFilter;
import com.akveo.bundlejava.ecommerce.entity.specification.OrderSpecification;
import com.akveo.bundlejava.ecommerce.entity.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

//@Repository
//public class OrderRepositoryImpl implements OrderRepository {
//
//    private EntityManager entityManager;
//
//    @Autowired
//    public OrderRepositoryImpl(EntityManagerFactory entityManagerFactory, OrderRepository) {
//        entityManager = entityManagerFactory.createEntityManager();
//    }
//
////    @Override
////    public List<Order> getFilteredListWithTotalCount(OrderGridFilter filter) {
////        int pageSize = filter.getPageSize();
////        int minFacet = pageSize * (filter.getPageNumber() - 1);
////        int maxFacet = pageSize * 10 + 1;
////        Query query = entityManager.createQuery(
////                "Select order FROM Order order, Country country JOIN order.country c WHERE order.id BETWEEN :minFacet AND :maxFacet", Order.class);
////        query.setParameter("minFacet", minFacet);
////        query.setParameter("maxFacet", maxFacet);
////        return query.getResultList();
////    }
//
//    @Override
//    public List<Order> getFilteredListWithTotalCount(OrderGridFilter filter) {
//
//    }
//}


