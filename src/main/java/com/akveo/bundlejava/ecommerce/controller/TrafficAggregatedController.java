package com.akveo.bundlejava.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/traffic-aggregated")
public class TrafficAggregatedController {
//    private TrafficAggregationService trafficAggregationService;
//
//    @Autowired
//    public TrafficAggregatedController(TrafficAggregationService trafficAggregationService) {
//        this.trafficAggregationService = trafficAggregationService;
//    }
//
//    @GetMapping("")
//    public ResponseEntity trafficStatistics(String filter) {
//        if(filter == null){
//            filter = "year";
//        }
//        return ok(trafficAggregationService.getDataForTable(filter));
//    }
}
