package com.akveo.bundlejava.ecommerce.entity.enums.converter;

import com.akveo.bundlejava.ecommerce.entity.enums.OrderStatusEnum;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrderStatusConverter implements AttributeConverter<OrderStatusEnum, Integer> {
    private final static Integer DEFAULT_STATUS_ID = 2;
    private final static OrderStatusEnum DEFAULT_ATTRIBUTE = OrderStatusEnum.PAYMENT;
    @Override
    public Integer convertToDatabaseColumn(OrderStatusEnum attribute) {
        attribute = attribute == null ? DEFAULT_ATTRIBUTE : attribute;
        switch (attribute) {
            case ALL:
                return 1;
            case PAYMENT:
                return 2;
            case CANCELLED:
                return 3;
            default:
                throw new IllegalArgumentException("Unknown" + attribute);
        }
    }

    @Override
    public OrderStatusEnum convertToEntityAttribute(Integer dbData) {
        dbData = dbData == null ? DEFAULT_STATUS_ID : dbData;
        switch (dbData) {
            case 1:
                return OrderStatusEnum.ALL;
            case 2:
                return OrderStatusEnum.PAYMENT;
            case 3:
                return OrderStatusEnum.CANCELLED;
            default:
                throw new IllegalArgumentException("Unknown" + dbData);
        }
    }
}
