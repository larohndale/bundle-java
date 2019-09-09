package com.akveo.bundlejava.ecommerce.entity.enums;

import java.util.stream.Stream;

public enum OrderTypeEnum {
    SOFAS(1),
    FURNITURE(2),
    LIGHTNING(3),
    TABLES(4),
    TEXTILES(5);

    private Integer value;

    OrderTypeEnum(Integer value) { this.value = value; }

    public int getValue() { return value; }

    public static String[] names() {
        return Stream.of(OrderTypeEnum.values()).map(OrderTypeEnum::name).toArray(String[]::new);
    }
}
