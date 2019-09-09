package com.akveo.bundlejava.ecommerce.repository;

import com.akveo.bundlejava.ecommerce.entity.Order;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CustomOrderAggregatedRepository {
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public CustomOrderAggregatedRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Integer> getChartDataForPeriod() {
        EntityManager em = entityManagerFactory.createEntityManager();
        Session session = em.unwrap(Session.class);
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);
        Root<Order> order = criteriaQuery.from(Order.class);
        Expression yearFunction = builder.function("year", Integer.class, order.get("date"));
        criteriaQuery.groupBy(yearFunction);
        criteriaQuery.multiselect(yearFunction, builder.count(order.get("date")));//в этой строчке нужно сделать группировку по годам(или месяцам)
        List<Tuple> results = em.createQuery(criteriaQuery).getResultList();
       return null;
    }
}
