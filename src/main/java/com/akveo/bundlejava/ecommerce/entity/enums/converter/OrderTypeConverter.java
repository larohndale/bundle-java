package com.akveo.bundlejava.ecommerce.entity.enums.converter;

import com.akveo.bundlejava.ecommerce.entity.enums.OrderStatusEnum;
import com.akveo.bundlejava.ecommerce.entity.enums.OrderTypeEnum;

import javax.persistence.AttributeConverter;

public class OrderTypeConverter implements AttributeConverter<OrderTypeEnum, Integer> {
    private final static Integer DEFAULT_TYPE_ID = 1;
    private final static OrderTypeEnum DEFAULT_ATTRIBUTE = OrderTypeEnum.SOFAS;
    @Override
    public Integer convertToDatabaseColumn(OrderTypeEnum attribute) {
        attribute = attribute == null ? DEFAULT_ATTRIBUTE : attribute;
        switch (attribute) {
            case SOFAS:
                return 1;
            case FURNITURE:
                return 2;
            case LIGHTNING:
                return 3;
            case TABLES:
                return 4;
            case TEXTILES:
                return 5;
            default:
                throw new IllegalArgumentException("Unknown" + attribute);
        }
    }

    @Override
    public OrderTypeEnum convertToEntityAttribute(Integer dbData) {
        dbData = dbData == null ? DEFAULT_TYPE_ID : dbData;
        switch (dbData) {
            case 1:
                return OrderTypeEnum.SOFAS;
            case 2:
                return OrderTypeEnum.FURNITURE;
            case 3:
                return OrderTypeEnum.LIGHTNING;
            case 4:
                return OrderTypeEnum.TABLES;
            case 5:
                return OrderTypeEnum.TEXTILES;
            default:
                throw new IllegalArgumentException("Unknown" + dbData);
        }
    }
}
