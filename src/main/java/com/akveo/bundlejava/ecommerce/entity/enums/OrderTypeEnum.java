package com.akveo.bundlejava.ecommerce.entity.enums;

import java.util.stream.Stream;

public enum OrderTypeEnum {
    SOFAS,
    FURNITURE,
    Lighting,
    Tables,
    Textiles;

    public static String[] names() {
        return Stream.of(OrderTypeEnum.values()).map(OrderTypeEnum::name).toArray(String[]::new);
    }
}
