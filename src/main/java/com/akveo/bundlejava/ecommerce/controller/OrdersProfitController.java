package com.akveo.bundlejava.ecommerce.controller;


import com.akveo.bundlejava.ecommerce.repository.CustomOrderAggregatedRepository;
import com.akveo.bundlejava.ecommerce.service.OrderAggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/orders-profit")
public class OrdersProfitController {
    private OrderAggregationService orderAggregationService;

    @Autowired
    public OrdersProfitController(OrderAggregationService orderAggregationService) {
        this.orderAggregationService = orderAggregationService;
    }

    //
//    @GetMapping("")
//    public ResponseEntity getProfitChartForYear(){
//        return ok(orderAggregationService.getProfitChartForYear());
//    }
//
//    @GetMapping("/short")
//    public ResponseEntity getProfitChartForTwoMonth(){
//        return ok(orderAggregationService.getProfitChartForTwoMonth());
//    }
//
    @GetMapping("/summary")
    public ResponseEntity getProfitSummary() {
        return ok(orderAggregationService.getProfitStatistic());
    }
}
