package com.akveo.bundlejava.ecommerce.controller;

import com.akveo.bundlejava.authentication.BundleUserDetailsService;
import com.akveo.bundlejava.ecommerce.DTO.OrderDTO;
import com.akveo.bundlejava.ecommerce.entity.filter.OrderGridFilter;
import com.akveo.bundlejava.ecommerce.service.OrderService;
import com.akveo.bundlejava.user.User;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity create(Authentication auth, @Valid @RequestBody OrderDTO orderDTO) {
        if (orderDTO.getId() != null) {
            return new ResponseEntity<>(
                    "Id mustn't be zero",
                    HttpStatus.BAD_REQUEST);
        }
        return ok(orderService.createOrder(auth, orderDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(Authentication auth, @PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
        if (!id.equals(orderDTO.getId())) {
            return new ResponseEntity<>(
                    "Id must be equal",
                    HttpStatus.BAD_REQUEST);
        }
        return ok(orderService.updateOrderById(id, orderDTO, auth));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(Authentication auth, @PathVariable Long id) {
        User user = ((BundleUserDetailsService.BundleUserDetails)auth.getPrincipal()).getUser();
        return ok(orderService.delete(id));
    }

}
