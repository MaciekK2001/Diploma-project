package com.example.database.dtos;

import com.example.database.entities.Activity;
import com.example.database.entities.ActivityType;
import com.example.database.entities.AppUser;
import com.example.database.entities.User;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class UserStatsGetDTO {

    User user;

    Integer avgCalories;

    ActivityType favActivityType;

    Activity lastActivity;

}
