package com.example.myapplication.entities

import java.util.Date
import java.util.UUID

data class AppUser (
    val appUserId: UUID,
    val firstName: String,
    val lastName: String,
    val about: String,
    val email: String,
    val joinedAt: Date
)

