package com.example.myapplication.dtos

import com.example.myapplication.model.Activity
import com.example.myapplication.model.ActivityType
import com.example.myapplication.model.AppUser

data class UserStatsGetDTO(
    val appUser: AppUser? = null,
    val avgCalories: Int = 0,
    val favActivityType: ActivityType? = null,
    val lastActivity: Activity? = null
)