package com.akveo.bundlejava.ecommerce.entity.enums;

public enum SortOrder {
    ASC,
    DESC,
    EMPTY;

    public static SortOrder valueOfIgnoreCase(String sortOrder) {
        sortOrder = sortOrder.toUpperCase();
        return valueOf(sortOrder);
    }
}
