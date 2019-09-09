package com.akveo.bundlejava.ecommerce.entity.enums;

import java.util.stream.Stream;

public enum PeriodFilterEnum {
    YEAR(1),
    MONTH(2),
    WEEK(3);
    private Integer value;

    PeriodFilterEnum(Integer value) { this.value = value; }

    public int getValue() { return value; }

    public static String[] names() {
        return Stream.of(OrderTypeEnum.values()).map(OrderTypeEnum::name).toArray(String[]::new);
    }
}
