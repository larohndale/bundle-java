package com.akveo.bundlejava.ecommerce.entity;

import java.math.BigDecimal;

public class AggregatedData<TGroup> {
    private TGroup group;
    private int count;
    private BigDecimal sum;

    public AggregatedData(TGroup group, int count, BigDecimal sum) {
        this.group = group;
        this.count = count;
        this.sum = sum;
    }

    public TGroup getGroup() {
        return group;
    }

    public void setGroup(TGroup group) {
        this.group = group;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}

