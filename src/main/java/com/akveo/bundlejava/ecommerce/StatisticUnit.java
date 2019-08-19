package com.akveo.bundlejava.ecommerce;

public class StatisticUnit<T> {
    private T value;
    private double trend;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public double getTrend() {
        return trend;
    }

    public void setTrend(double trend) {
        this.trend = trend;
    }
}
