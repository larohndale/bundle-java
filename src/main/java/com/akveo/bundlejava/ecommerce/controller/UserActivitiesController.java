package com.akveo.bundlejava.ecommerce.controller;

import com.akveo.bundlejava.ecommerce.service.UserActivityAggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.http.ResponseEntity.ok;

public class UserActivitiesController {

    private UserActivityAggregationService userActivityService;


    @Autowired
    public UserActivitiesController(UserActivityAggregationService userActivityService) {
        this.userActivityService = userActivityService;
    }

//    @GetMapping("/user-activity")
//    public ResponseEntity userActivities(String date) {
//        if(date == null){
//            date = "year";
//        }
//
//        return ok(userActivityService.getDataForTable());
//    }

}
