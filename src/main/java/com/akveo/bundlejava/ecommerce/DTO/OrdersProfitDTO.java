package com.akveo.bundlejava.ecommerce.DTO;

import com.akveo.bundlejava.ecommerce.StatisticUnit;

public class OrdersProfitDTO {
    private StatisticUnit<Integer> todayProfit;
    private StatisticUnit<Integer> weekOrdersProfit;
    private StatisticUnit<Integer> weekCommentsProfit;
}
