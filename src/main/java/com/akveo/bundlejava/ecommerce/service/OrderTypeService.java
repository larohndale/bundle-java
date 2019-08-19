package com.akveo.bundlejava.ecommerce.service;

import com.akveo.bundlejava.ecommerce.entity.enums.OrderTypeEnum;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderTypeService {
    public List<String> getList() {
        return Arrays.asList(OrderTypeEnum.names());
    }
}
