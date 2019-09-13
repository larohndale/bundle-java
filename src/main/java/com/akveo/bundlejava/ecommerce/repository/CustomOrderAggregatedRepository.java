package com.akveo.bundlejava.ecommerce.repository;

import com.akveo.bundlejava.ecommerce.entity.Order;
import com.akveo.bundlejava.ecommerce.entity.enums.AggregationEnum;
import com.akveo.bundlejava.ecommerce.entity.enums.OrderStatusEnum;
import com.akveo.bundlejava.ecommerce.entity.statistic.OrdersProfit;
import com.akveo.bundlejava.ecommerce.entity.statistic.OrdersSummary;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class CustomOrderAggregatedRepository {
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public CustomOrderAggregatedRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private Map<Integer, Long> generateEmptyCountMapForWeek() {
        Map<Integer, Long> emptyMap = new TreeMap<>();
        int weekDayNumber = 1;
        for (int i = 1; i < 7; i++) {
            emptyMap.put(weekDayNumber++, 0L);
        }
        return emptyMap;
    }

    private Map<Integer, Long> generateEmptyCountMapForMonth() {
        Map<Integer, Long> emptyMap = new TreeMap<>();
        int monthNumber = 1;
        for (int i = 0; i < 12; i++) {
            emptyMap.put(monthNumber++, 0L);
        }
        return emptyMap;
    }

    private Map<Integer, Long> generateEmptyCountMapForYear(int startYear, int endYear) {
        final int interval = Math.abs(endYear - startYear);
        Map<Integer, Long> emptyMap = new TreeMap<>();
        for (int i = 0; i < interval; i++) {
            emptyMap.put(startYear++, 0L);
        }
        return emptyMap;
    }

    private Map<Integer, Double> generateEmptyProfitMapForWeek() {
        Map<Integer, Double> emptyMap = new TreeMap<>();
        int weekDayNumber = 1;
        for (int i = 0; i < 7; i++) {
            emptyMap.put(weekDayNumber++, 0.0);
        }
        return emptyMap;
    }

    private Map<Integer, Double> generateEmptyProfitMapForMonth() {
        Map<Integer, Double> emptyMap = new TreeMap<>();
        int monthNumber = 1;
        for (int i = 0; i < 12; i++) {
            emptyMap.put(monthNumber++, 0.0);
        }
        return emptyMap;
    }

    private Map<Integer, Double> generateEmptyProfitMapForYear(int startYear, int endYear) {
        final int interval = Math.abs(endYear - startYear);
        Map<Integer, Double> emptyMap = new TreeMap<>();
        for (int i = 0; i < interval; i++) {
            emptyMap.put(startYear++, 0.0);
        }
        return emptyMap;
    }

    public Map<Integer, Long> getChartDataForPeriod(LocalDateTime startDate, LocalDateTime endDate,
                                                    OrderStatusEnum status, AggregationEnum aggregation) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);
            Root<Order> order = criteriaQuery.from(Order.class);

            Map<Integer, Long> emptyMap;
            Expression groupFunction = null;
            switch (aggregation) {
                case WEEK:
                    groupFunction = builder.function("dayOfWeek", Integer.class, order.get("date"));
                    emptyMap = generateEmptyCountMapForWeek();
                    break;
                case YEAR:
                    groupFunction = builder.function("year", Integer.class, order.get("date"));
                    emptyMap = generateEmptyCountMapForYear(startDate.getYear(), endDate.getYear());
                    break;
                case MONTH:
                    groupFunction = builder.function("month", Integer.class, order.get("date"));
                    emptyMap = generateEmptyCountMapForMonth();
                    break;
                default:
                    emptyMap = new TreeMap<>();
            }

            criteriaQuery.where(builder.and(builder.between(order.get("date"), startDate, endDate)),
                    builder.and(builder.equal(order.get("status"), status.getValue()))
            );
            criteriaQuery.groupBy(groupFunction);
            criteriaQuery.multiselect(groupFunction, builder.count(order.get("date")));

            List<Tuple> queryResult = entityManager.createQuery(criteriaQuery)
                    .getResultList();

            queryResult.forEach(tupleData -> {
                Integer tupleKey = (Integer) tupleData.get(0);
                Long tupleValue = (Long) tupleData.get(1);
                emptyMap.put(tupleKey, tupleValue);
            });

            return emptyMap;
        }
    }

    public Map<Integer, Double> getProfitDataForChart(LocalDateTime startDate, LocalDateTime endDate,
                                                      OrderStatusEnum status, AggregationEnum aggregation) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try (Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);
            Root<Order> order = criteriaQuery.from(Order.class);

            Map<Integer, Double> emptyMap;
            Expression groupFunction = null;
            switch (aggregation) {
                case WEEK:
                    groupFunction = builder.function("dayOfWeek", Integer.class, order.get("date"));
                    emptyMap = generateEmptyProfitMapForWeek();
                    break;
                case YEAR:
                    groupFunction = builder.function("year", Integer.class, order.get("date"));
                    emptyMap = generateEmptyProfitMapForYear(startDate.getYear(), endDate.getYear());
                    break;
                case MONTH:
                    groupFunction = builder.function("month", Integer.class, order.get("date"));
                    emptyMap = generateEmptyProfitMapForMonth();
                    break;
                default:
                    emptyMap = new TreeMap<>();
            }

            criteriaQuery.where(builder.and(builder.between(order.get("date"), startDate, endDate)),
                    builder.and(builder.equal(order.get("status"), status.getValue()))
            );
            criteriaQuery.groupBy(groupFunction);
            criteriaQuery.multiselect(groupFunction, builder.sumAsDouble(order.get("value")));

            List<Tuple> queryResult = entityManager.createQuery(criteriaQuery)
                    .getResultList();

            queryResult.forEach(tupleData -> {

                Integer tupleKey = (Integer) tupleData.get(0);
                Double tupleValue = ((BigDecimal) tupleData.get(1)).doubleValue();

                emptyMap.put(tupleKey, tupleValue);
            });

            return emptyMap;
        }
    }

    private CriteriaQuery<Long> buildCriteriaQueryForCount(CriteriaBuilder builder,
                                                           LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime today = LocalDateTime.now();
        if (startDate == null) {
            startDate = today.minusYears(10);
        }
        if (endDate == null) {
            endDate = today;
        }

        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Order> order = criteriaQuery.from(Order.class);

        criteriaQuery.select(builder.count(order));
        criteriaQuery.where(builder.between(order.get("date"), startDate, endDate));

        return criteriaQuery;

    }

    private CriteriaQuery<Double> buildCriteriaQueryForProfit(CriteriaBuilder builder,
                                                              LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime today = LocalDateTime.now();
        if (startDate == null) {
            startDate = today.minusYears(10);
        }
        if (endDate == null) {
            endDate = today;
        }

        CriteriaQuery<Double> profitCriteriaQuery = builder.createQuery(Double.class);
        Root<Order> profitOrder = profitCriteriaQuery.from(Order.class);

        profitCriteriaQuery.select(builder.sumAsDouble(profitOrder.get("value")));
        profitCriteriaQuery.where(builder.between(profitOrder.get("date"), startDate, endDate));

        return profitCriteriaQuery;

    }

    public OrdersSummary getOrdersSummaryInfo() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDate;
        LocalDateTime endDate;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            int countForAllTime = Math.toIntExact(entityManager
                    .createQuery(buildCriteriaQueryForCount(builder, null, null)).getSingleResult());

            startDate = LocalDateTime.of(today.getYear(),
                    today.minusMonths(1).getMonth(), 1, 0, 1);
            endDate = LocalDateTime.of(today.getYear(),
                    today.getMonth(), 1, 23, 59).minusDays(1);
            int countForLastMonth = Math.toIntExact(entityManager
                    .createQuery(buildCriteriaQueryForCount(builder, startDate, endDate)).getSingleResult());

            LocalDateTime weekBefore = LocalDateTime.now().minusDays(7);
            Pair<LocalDateTime, LocalDateTime> weekInterval = AggregationEnum.getWeekInterval(weekBefore);
            startDate = weekInterval.getFirst();
            endDate = weekInterval.getSecond();
            int countForLastWeek = Math.toIntExact(entityManager
                    .createQuery(buildCriteriaQueryForCount(builder, startDate, endDate)).getSingleResult());

            LocalDateTime startDay = LocalDateTime.of(today.getYear(), today.getMonth(),
                    today.getDayOfMonth(), 0, 1);
            LocalDateTime endDay = LocalDateTime.of(today.getYear(), today.getMonth(),
                    today.getDayOfMonth(), 23, 59);
            int countForLastToday = Math.toIntExact(entityManager
                    .createQuery(buildCriteriaQueryForCount(builder, startDay, endDay)).getSingleResult());

            return new OrdersSummary(countForAllTime, countForLastMonth, countForLastWeek, countForLastToday);
        }
    }

    public OrdersProfit getProfitInfo() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try (Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilder builder = session.getCriteriaBuilder();

            LocalDateTime today = LocalDateTime.now();
            LocalDateTime weekBefore = LocalDateTime.now().minusDays(7);
            Pair<LocalDateTime, LocalDateTime> weekInterval = AggregationEnum.getWeekInterval(weekBefore);

            LocalDateTime lastWeekStartDate = weekInterval.getFirst();
            LocalDateTime lastWeekEndDate = weekInterval.getSecond();
            LocalDateTime currentWeekStartDate = lastWeekEndDate.plusDays(1);

            int countForLastWeek = Math.toIntExact(entityManager
                    .createQuery(buildCriteriaQueryForCount(builder, lastWeekStartDate, lastWeekEndDate))
                    .getSingleResult());

            double profitForLastWeek = (entityManager
                    .createQuery(buildCriteriaQueryForProfit(builder, lastWeekStartDate, lastWeekEndDate)))
                    .getSingleResult();

            int countForCurrentWeek = Math.toIntExact(entityManager
                    .createQuery(buildCriteriaQueryForCount(builder, currentWeekStartDate, today))
                    .getSingleResult());

            double profitForCurrentWeek = (entityManager
                    .createQuery(buildCriteriaQueryForProfit(builder, currentWeekStartDate, today)))
                    .getSingleResult();

            return new OrdersProfit(countForLastWeek, countForCurrentWeek, profitForLastWeek, profitForCurrentWeek);
        }
    }
}
