package com.akveo.bundlejava.ecommerce.entity.statistic;

public class OrdersProfit {
    private int lastWeekCount;
    private int currentWeekCount;
    private double lastWeekProfit;
    private double currentWeekProfit;

    public OrdersProfit(int lastWeekCount, int currentWeekCount, double lastWeekProfit, double currentWeekProfit) {
        this.lastWeekCount = lastWeekCount;
        this.currentWeekCount = currentWeekCount;
        this.lastWeekProfit = lastWeekProfit;
        this.currentWeekProfit = currentWeekProfit;
    }

    public OrdersProfit() {
    }

    public int getLastWeekCount() {
        return lastWeekCount;
    }

    public void setLastWeekCount(int lastWeekCount) {
        this.lastWeekCount = lastWeekCount;
    }

    public int getCurrentWeekCount() {
        return currentWeekCount;
    }

    public void setCurrentWeekCount(int currentWeekCount) {
        this.currentWeekCount = currentWeekCount;
    }

    public double getLastWeekProfit() {
        return lastWeekProfit;
    }

    public void setLastWeekProfit(double lastWeekProfit) {
        this.lastWeekProfit = lastWeekProfit;
    }

    public double getCurrentWeekProfit() {
        return currentWeekProfit;
    }

    public void setCurrentWeekProfit(double currentWeekProfit) {
        this.currentWeekProfit = currentWeekProfit;
    }
}
