package com.akveo.bundlejava.ecommerce.service;

import com.akveo.bundlejava.ecommerce.DTO.BaseChartDTO;
import com.akveo.bundlejava.ecommerce.repository.CustomOrderAggregatedRepository;

import com.akveo.bundlejava.ecommerce.repository.OrderAggregatedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderAggregationService {
    private OrderAggregatedRepository orderAggregatedRepository;
    private CustomOrderAggregatedRepository customOrderAggregatedRepository;

    @Autowired
    public OrderAggregationService(OrderAggregatedRepository orderAggregatedRepository,
                                   CustomOrderAggregatedRepository customOrderAggregatedRepository) {
        this.orderAggregatedRepository = orderAggregatedRepository;
        this.customOrderAggregatedRepository = customOrderAggregatedRepository;
    }

//    public BaseChartDTO<Integer> getCountDataForChart(String aggregation) {
//        BaseChartDTO<Integer> stats = new BaseChartDTO<>();
//
//        List<ChartDataDTO<Integer>> lines = new ArrayList<>();
//
//        ChartDataDTO<Integer> data1 = new ChartDataDTO<>();
//        data1.setValues(new ArrayList<>());
//        ChartDataDTO<Integer> data2 = new ChartDataDTO<>();
//        data2.setValues(new ArrayList<>());
//        ChartDataDTO<Integer> data3 = new ChartDataDTO<>();
//        data3.setValues(new ArrayList<>());
//
//        int[] dataValues1  = {0,4,3,8,4,10,9};
//        List<Integer> like1 = IntStream.of( dataValues1 ).boxed().collect( Collectors.toList() );
//        int[] dataValues2  = {0,8,7,8,4,9,5};
//        List<Integer> like2 = IntStream.of( dataValues2 ).boxed().collect( Collectors.toList() );
//        int[] dataValues3  = {0,12,10,16,8,19,14};
//        List<Integer> like3 = IntStream.of( dataValues3 ).boxed().collect( Collectors.toList() );
//
//        like1.forEach(value -> data1.getValues().add(value));
//        like2.forEach(value -> data2.getValues().add(value));
//        like3.forEach(value -> data3.getValues().add(value));
//
//        data1.setType("Payment");
//        data2.setType("Cancelled");
//        data3.setType("All");
//
//        lines.add(data1);
//        lines.add(data2);
//        lines.add(data3);
//
//        stats.setAxisXLabels(new ArrayList<>(Arrays.asList("Sun","Mon","Tue","Wed","Thu","Fri","Sat")));
//
//        stats.setLines(lines);
//
//
//        return stats;
//    }

    public BaseChartDTO<Integer> getCountDataForChart(String aggregation) {
        List<Integer> aggr =  customOrderAggregatedRepository.getChartDataForPeriod();
        return null;
    }


    private LocalDateTime defineStartOfWeek(LocalDateTime dayOfWeek) {
        while (dayOfWeek.getDayOfWeek() != DayOfWeek.SUNDAY) {
            dayOfWeek = dayOfWeek.minusDays(1);
        }
        return dayOfWeek;
    }

    private LocalDateTime defineEndOfWeek(LocalDateTime dayOfWeek) {
        while (dayOfWeek.getDayOfWeek() != DayOfWeek.SATURDAY) {
            dayOfWeek = dayOfWeek.plusDays(1);
        }
        return dayOfWeek;
    }

}
