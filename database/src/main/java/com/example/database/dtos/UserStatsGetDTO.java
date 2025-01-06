package com.example.database.dtos;

import com.example.database.entities.Activity;
import com.example.database.entities.ActivityType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class UserStatsGetDTO {

    UserDTO userDTO;

    Integer avgCalories;

    ActivityType favActivityType;

    Activity lastActivity;

}
