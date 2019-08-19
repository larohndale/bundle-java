package com.akveo.bundlejava.ecommerce.entity.enums;

import java.util.stream.Stream;

public enum OrderStatusEnum {
    ALL(-1),
    PAYMENT(1),
    CANCELLED(0);
    private final int code;

    private OrderStatusEnum(int code) {
        this.code = code;
    }

    public static String[] names() {
        return Stream.of(OrderTypeEnum.values()).map(OrderTypeEnum::name).toArray(String[]::new);
    }

    public int code() {
        return code;
    }
}
