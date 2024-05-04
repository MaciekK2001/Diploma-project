package com.example.database.dtos;

import com.example.database.entities.Activity;
import com.example.database.entities.ActivityType;
import com.example.database.entities.AppUser;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class UserStatsGetDTO {

    AppUser appUser;

    Integer avgCalories;

    ActivityType favActivityType;

    Activity lastActivity;

}
