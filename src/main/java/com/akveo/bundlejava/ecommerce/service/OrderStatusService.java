package com.akveo.bundlejava.ecommerce.service;

import com.akveo.bundlejava.ecommerce.entity.enums.OrderStatusEnum;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderStatusService {
    public List<String> getList() {
        return Arrays.asList(OrderStatusEnum.names());
    }
}
