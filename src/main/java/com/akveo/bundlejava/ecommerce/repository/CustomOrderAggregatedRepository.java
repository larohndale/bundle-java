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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> order = criteriaQuery.from(Order.class);
        //criteriaQuery.select(order , criteriaBuilder.count(order)).groupBy(order.get("date").get("year"));
        criteriaQuery.select(order.get("date"));//в этой строчке нужно сделать группировку по годам(или месяцам)

//        List result = session.createCriteria(Order.class)
//                .add(Restrictions.between("date",  LocalDateTime.now().minusYears(4), LocalDateTime.now()))
//                .setProjection(Projections.groupProperty("date")).list();
//setProjection(Projections.sqlGroupProjection("year(date)", "year(date)",new String[]{"XXX"}, new Type[]{Hibernate.DATE})).list();

        List<Order> results = em.createQuery(criteriaQuery).getResultList();
       return null;
    }
}
