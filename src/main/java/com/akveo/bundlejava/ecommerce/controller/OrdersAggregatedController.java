package com.akveo.bundlejava.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/orders-aggregated")
public class OrdersAggregatedController {
//    private OrderAggregationService orderAggregationService;
//
//    @Autowired
//    public OrdersAggregatedController(OrderAggregationService orderAggregationService) {
//        this.orderAggregationService = orderAggregationService;
//    }
//
//    @GetMapping("")
//    public ResponseEntity getCountDataForChart(String aggregation) {
//        if (aggregation == null) {
//            aggregation = "year";
//        }
//        return ok(orderAggregationService.getCountDataForChart(aggregation));
//    }
//
//    @GetMapping("/profit")
//    public ResponseEntity getProfitDataForChart(String aggregation) {
//        if (aggregation == null) {
//            aggregation = "year";
//        }
//        return ok(orderAggregationService.getProfitDataForChart(aggregation));
//    }
//
//    @GetMapping("/country")
//    public ResponseEntity getStatisticByCountry(String countryCode) {
//        return ok(orderAggregationService.getStatisticByCountry(countryCode));
//    }
//
//    @GetMapping("/summary")
//    public ResponseEntity getOrdersSummaryInfo() {
//        return ok(orderAggregationService.getOrdersSummaryInfo());
//    }
}
