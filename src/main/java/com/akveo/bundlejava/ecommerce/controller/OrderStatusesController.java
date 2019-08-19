package com.akveo.bundlejava.ecommerce.controller;

import com.akveo.bundlejava.ecommerce.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/order-statuses")
public class OrderStatusesController {
    private OrderStatusService orderStatusService;

    @Autowired
    OrderStatusesController(OrderStatusService orderStatusService){
        this.orderStatusService = orderStatusService;
    }

    @GetMapping("")
    public ResponseEntity getAll(){
        return ok(orderStatusService.getList());
    }

}
