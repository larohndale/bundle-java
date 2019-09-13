package com.akveo.bundlejava.ecommerce.DTO;

import com.akveo.bundlejava.ecommerce.StatisticUnit;

public class OrdersProfitDTO {
    private StatisticUnit<Integer> todayProfit;
    private StatisticUnit<Integer> weekOrdersProfit;
    private StatisticUnit<Integer> weekCommentsProfit;

    public StatisticUnit<Integer> getTodayProfit() {
        return todayProfit;
    }

    public void setTodayProfit(StatisticUnit<Integer> todayProfit) {
        this.todayProfit = todayProfit;
    }

    public StatisticUnit<Integer> getWeekOrdersProfit() {
        return weekOrdersProfit;
    }

    public void setWeekOrdersProfit(StatisticUnit<Integer> weekOrdersProfit) {
        this.weekOrdersProfit = weekOrdersProfit;
    }

    public StatisticUnit<Integer> getWeekCommentsProfit() {
        return weekCommentsProfit;
    }

    public void setWeekCommentsProfit(StatisticUnit<Integer> weekCommentsProfit) {
        this.weekCommentsProfit = weekCommentsProfit;
    }

    public OrdersProfitDTO(StatisticUnit<Integer> todayProfit, StatisticUnit<Integer> weekOrdersProfit, StatisticUnit<Integer> weekCommentsProfit) {
        this.todayProfit = todayProfit;
        this.weekOrdersProfit = weekOrdersProfit;
        this.weekCommentsProfit = weekCommentsProfit;


    }
}
