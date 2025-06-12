package com.example.database.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class MonthlyStatisticsDTO {
    String yearMonth;
    Integer sumOfCalories;

    public MonthlyStatisticsDTO(String yearMonth, Integer sumOfCalories) {
        this.yearMonth = yearMonth;
        this.sumOfCalories = sumOfCalories;
    }
}
