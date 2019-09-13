package com.akveo.bundlejava.ecommerce.entity.statistic;

public class OrdersSummary {
    private int lastMonth;
    private int lastWeek;
    private int today;
    private int marketplace;

    public OrdersSummary(int marketplace, int lastMonth, int lastWeek, int today) {
        this.marketplace = marketplace;
        this.lastMonth = lastMonth;
        this.lastWeek = lastWeek;
        this.today = today;
    }

    public int getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(int lastMonth) {
        this.lastMonth = lastMonth;
    }

    public int getLastWeek() {
        return lastWeek;
    }

    public void setLastWeek(int lastWeek) {
        this.lastWeek = lastWeek;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public int getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(int marketplace) {
        this.marketplace = marketplace;
    }
}