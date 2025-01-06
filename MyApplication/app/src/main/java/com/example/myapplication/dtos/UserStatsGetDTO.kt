package com.example.myapplication.dtos

import com.example.myapplication.model.Activity
import com.example.myapplication.model.ActivityType

data class UserStatsGetDTO(
    val userDTO: UserDTO? = null,
    val avgCalories: Int = 0,
    val favActivityType: ActivityType? = null,
    val lastActivity: Activity? = null
)