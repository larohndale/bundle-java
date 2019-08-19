package com.akveo.bundlejava.ecommerce.controller;

import com.akveo.bundlejava.ecommerce.DTO.OrderDTO;
import com.akveo.bundlejava.ecommerce.entity.filter.OrderGridFilter;
import com.akveo.bundlejava.ecommerce.service.OrderService;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    private OrderService orderService;

    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(method=RequestMethod.GET, path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDataForGrid(OrderGridFilter orderGridFilter) {
        return ok(orderService.getDataForGrid(orderGridFilter));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        return ok(orderService.getOrderById(id));
    }

    @PostMapping("")
    public ResponseEntity create(OrderDTO orderDTO) {
        if (orderDTO.getId() != 0) {
            return new ResponseEntity<>(
                    "Id mustn't be zero",
                    HttpStatus.BAD_REQUEST);
        }
        return ok(orderService.createOrder(orderDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable Long id, OrderDTO orderDTO) {
        if (!id.equals(orderDTO.getId())) {
            return new ResponseEntity<>(
                    "Id must be equal",
                    HttpStatus.BAD_REQUEST);
        }
        return ok(orderService.updateOrderById(id, orderDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return ok(orderService.delete(id));
    }


}
