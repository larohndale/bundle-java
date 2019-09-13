package com.akveo.bundlejava.ecommerce.entity.enums;



import org.springframework.data.util.Pair;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public enum AggregationEnum {
    YEAR, MONTH, WEEK;

    public static Pair<LocalDateTime, LocalDateTime> getWeekInterval(LocalDateTime dayOfWeek) {
        while (dayOfWeek.getDayOfWeek() != DayOfWeek.MONDAY) {
            dayOfWeek = dayOfWeek.minusDays(1);
        }

        LocalDateTime startWeek = LocalDateTime.of(dayOfWeek.getYear(), dayOfWeek.getMonth(),
                dayOfWeek.getDayOfMonth(), 0, 1).minusDays(1);//in sql week starts from Sunday

        LocalDateTime endWeek = startWeek.plusDays(7);

        return Pair.of(startWeek, endWeek);
    }
}
