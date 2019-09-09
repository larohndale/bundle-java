package com.akveo.bundlejava.ecommerce.entity.enums;

import java.util.stream.Stream;

public enum OrderStatusEnum {
    PAYMENT(1),
    CANCELLED(2),
    ALL(3);

    private int value;

    OrderStatusEnum(int value) { this.value = value; }

    public int getValue() { return value; }

        public static String[] names() {
        return Stream.of(OrderStatusEnum.values()).map(OrderStatusEnum::name).toArray(String[]::new);
    }
}
