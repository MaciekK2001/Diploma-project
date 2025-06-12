package com.example.database.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.sql.Date;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class DailyStatisticsDTO {
    Date date;

    Integer sumOfCalories;
}
