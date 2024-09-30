package com.example.myapplication.dtos

import com.example.myapplication.entities.Activity
import com.example.myapplication.entities.ActivityType
import com.example.myapplication.entities.AppUser

data class UserStatsGetDTO(
    val appUser: AppUser? = null,
    val avgCalories: Int = 0,
    val favActivityType: ActivityType? = null,
    val lastActivity: Activity? = null
)