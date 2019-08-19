package com.akveo.bundlejava.ecommerce.DTO;

public class OrderTypeStatisticDTO {
    private Long orderTypeId;
    private int count;

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
