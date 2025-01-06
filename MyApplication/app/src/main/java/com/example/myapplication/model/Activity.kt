package com.example.myapplication.model

import java.util.Date
import java.util.UUID

data class Activity(
    val activityId: UUID,
    val user: User,
    val burntCalories: Int,
    val time: Long,
    val createdAt: Date,
    val activityType: ActivityType
)