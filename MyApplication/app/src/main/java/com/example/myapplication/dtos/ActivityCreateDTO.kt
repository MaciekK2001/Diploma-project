package com.example.myapplication.dtos

import com.example.myapplication.model.ActivityType

data class ActivityCreateDTO(
    val burntCalories: Int,
    val time: Long,
    val type: ActivityType
)