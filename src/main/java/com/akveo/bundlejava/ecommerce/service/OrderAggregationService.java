package com.akveo.bundlejava.ecommerce.service;

import com.akveo.bundlejava.ecommerce.DTO.BaseChartDTO;
import com.akveo.bundlejava.ecommerce.DTO.ChartDataDTO;
import com.akveo.bundlejava.ecommerce.DTO.OrdersProfitDTO;
import com.akveo.bundlejava.ecommerce.DTO.OrdersSummaryDTO;
import com.akveo.bundlejava.ecommerce.StatisticUnit;
import com.akveo.bundlejava.ecommerce.entity.enums.AggregationEnum;
import com.akveo.bundlejava.ecommerce.entity.enums.OrderStatusEnum;
import com.akveo.bundlejava.ecommerce.entity.statistic.OrdersProfit;
import com.akveo.bundlejava.ecommerce.entity.statistic.OrdersSummary;
import com.akveo.bundlejava.ecommerce.repository.CustomOrderAggregatedRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderAggregationService {
    private CustomOrderAggregatedRepository customOrderAggregatedRepository;
    private ModelMapper modelMapper;

    @Autowired
    public OrderAggregationService(CustomOrderAggregatedRepository customOrderAggregatedRepository,
                                   ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.customOrderAggregatedRepository = customOrderAggregatedRepository;
    }

    private List<Long> calculateAllStatuses(List<Long> paymentValues, List<Long> cancelledValues) {
        List<Long> allStatuses = new ArrayList<>();
        for (int i = 0; i < paymentValues.size(); i++) {
            allStatuses.add(paymentValues.get(i) + cancelledValues.get(i));
        }
        return allStatuses;
    }

    private List<Double> calculateAllStatusesForProfit(List<Double> paymentValues, List<Double> cancelledValues) {
        List<Double> allStatuses = new ArrayList<>();
        for (int i = 0; i < paymentValues.size(); i++) {
            allStatuses.add(paymentValues.get(i) + cancelledValues.get(i));
        }
        return allStatuses;
    }

    private List<String> getAxisLabelsByAggregation(Set<Integer> values, AggregationEnum aggregation){
        List<String> labels = null;
        switch(aggregation){
            case WEEK:
                labels =  Arrays.asList("Mon","Tue","Wed","Thu","Fri","Sat","Sun");
                break;
            case MONTH:
                labels = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
                break;
            case YEAR:
                labels = values.stream()
                        .map(val -> Integer.toString(val))
                        .collect(Collectors.toList());
                break;
        }
        return labels;
    }

    public BaseChartDTO<Long> getCountDataForChart(String aggregation) {
        BaseChartDTO<Long> stats = new BaseChartDTO<>();
        List<ChartDataDTO<Long>> lines = new ArrayList<>();

        AggregationEnum aggregationParameter = AggregationEnum.valueOf(aggregation.toUpperCase());

        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        switch (aggregationParameter) {
            case WEEK:
                LocalDateTime randomDayOfWeek = LocalDateTime.now().minusDays(7);
                Pair<LocalDateTime, LocalDateTime> weekInterval = AggregationEnum.getWeekInterval(randomDayOfWeek);
                startDate = weekInterval.getFirst();
                endDate = weekInterval.getSecond();
            break;
            case MONTH:
                int yearBefore = LocalDateTime.now().minusYears(1).getYear();
                startDate = LocalDateTime.of(
                        yearBefore, Month.JANUARY, 1, 1, 1
                );
                endDate = LocalDateTime.of(
                        yearBefore, Month.DECEMBER, 31, 1, 1
                );
                break;
            case YEAR:
                startDate = LocalDateTime.now().minusYears(9);
                endDate = LocalDateTime.now();
                break;
        }

        Map<Integer, Long> paymentStatusAggr = customOrderAggregatedRepository
                .getChartDataForPeriod(startDate, endDate, OrderStatusEnum.PAYMENT, aggregationParameter);
        Map<Integer, Long> cancelledStatusAggr = customOrderAggregatedRepository
                .getChartDataForPeriod(startDate, endDate, OrderStatusEnum.CANCELLED, aggregationParameter);

        List<Long> paymentValues = new ArrayList<>(paymentStatusAggr.values());
        List<Long> cancelledValues = new ArrayList<>(cancelledStatusAggr.values());
        List<Long> allValues = calculateAllStatuses(paymentValues, cancelledValues);

        List<String> axisXLabels = getAxisLabelsByAggregation(paymentStatusAggr.keySet(), aggregationParameter);

        lines.add(new ChartDataDTO<>(paymentValues, "Payment"));
        lines.add(new ChartDataDTO<>(cancelledValues, "Cancelled"));
        lines.add(new ChartDataDTO<>(allValues, "All"));

        stats.setAxisXLabels(axisXLabels);

        stats.setLines(lines);

        return stats;
    }

    public BaseChartDTO<Double> getProfitDataForChart(String aggregation){
        BaseChartDTO<Double> stats = new BaseChartDTO<>();
        List<ChartDataDTO<Double>> lines = new ArrayList<>();

        AggregationEnum aggregationParameter = AggregationEnum.valueOf(aggregation.toUpperCase());

        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        switch (aggregationParameter) {
            case WEEK:
                LocalDateTime randomDayOfWeek = LocalDateTime.now().minusDays(7);
                Pair<LocalDateTime, LocalDateTime> weekInterval = AggregationEnum.getWeekInterval(randomDayOfWeek);
                startDate = weekInterval.getFirst();
                endDate = weekInterval.getSecond();
                break;
            case MONTH:
                int yearBefore = LocalDateTime.now().minusYears(1).getYear();
                startDate = LocalDateTime.of(
                        yearBefore, Month.JANUARY, 1, 1, 1
                );
                endDate = LocalDateTime.of(
                        yearBefore, Month.DECEMBER, 31, 1, 1
                );
                break;
            case YEAR:
                startDate = LocalDateTime.now().minusYears(9);
                endDate = LocalDateTime.now();
                break;
        }

        Map<Integer, Double> paymentStatusAggr = customOrderAggregatedRepository
                .getProfitDataForChart(startDate, endDate, OrderStatusEnum.PAYMENT, aggregationParameter);
        Map<Integer, Double> cancelledStatusAggr = customOrderAggregatedRepository
                .getProfitDataForChart(startDate, endDate, OrderStatusEnum.CANCELLED, aggregationParameter);

        List<Double> paymentValues = new ArrayList<>(paymentStatusAggr.values());
        List<Double> cancelledValues = new ArrayList<>(cancelledStatusAggr.values());
        List<Double> allValues = calculateAllStatusesForProfit(paymentValues, cancelledValues);

        List<String> axisXLabels = getAxisLabelsByAggregation(paymentStatusAggr.keySet(), aggregationParameter);

        lines.add(new ChartDataDTO<>(paymentValues, "Payment"));
        lines.add(new ChartDataDTO<>(cancelledValues, "Cancelled"));
        lines.add(new ChartDataDTO<>(allValues, "All"));

        stats.setAxisXLabels(axisXLabels);

        stats.setLines(lines);

        return stats;
    }

    public OrdersSummaryDTO getOrdersSummaryInfo() {
        OrdersSummary summaryInfo = customOrderAggregatedRepository.getOrdersSummaryInfo();

        return modelMapper.map(summaryInfo, OrdersSummaryDTO.class);
    }

    private int calculateMinutesForTime(LocalDateTime time){
        return time.getHour() * 60 + time.getMinute();
    }

    public OrdersProfitDTO getProfitStatistic(){
        OrdersProfit profitInfo = customOrderAggregatedRepository.getProfitInfo();
        int yesterdayCommentsCount = calculateMinutesForTime(LocalDateTime.now().plusHours(12).plusMinutes(37));
        int currentCommentsCount = calculateMinutesForTime(LocalDateTime.now());

        int trend = yesterdayCommentsCount == 0 ? 0 : (int) Math.round(
                (double) (currentCommentsCount - yesterdayCommentsCount) / yesterdayCommentsCount * 100);

        StatisticUnit<Integer> weekCommentsProfit = new StatisticUnit<>(currentCommentsCount, trend);

        int value = profitInfo.getCurrentWeekCount();
        trend = profitInfo.getLastWeekCount() == 0 ? 0 : (int) Math.round(
                (double) (value - profitInfo.getLastWeekCount()) / profitInfo.getLastWeekCount() * 100);

        StatisticUnit<Integer> weekOrdersProfit = new StatisticUnit<>(value, trend);

        int profitValue = (int)profitInfo.getCurrentWeekProfit();
        trend = profitInfo.getLastWeekProfit() == 0 ? 0 : (int) Math.round(
                 (profitValue - profitInfo.getLastWeekProfit()) / profitInfo.getLastWeekProfit() * 100);

        StatisticUnit<Integer> todayProfit = new StatisticUnit<>(profitValue, trend);

        return new OrdersProfitDTO(todayProfit, weekOrdersProfit, weekCommentsProfit);

    }




}
