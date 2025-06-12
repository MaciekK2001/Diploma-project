package com.example.database.dtos;

import com.example.database.entities.ActivityType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;

@Value
@Builder
@Jacksonized
public class ActivityCreateDTO {

    Integer burntCalories;

    Long time;

    ActivityType type;

    Timestamp activityDate;
}
