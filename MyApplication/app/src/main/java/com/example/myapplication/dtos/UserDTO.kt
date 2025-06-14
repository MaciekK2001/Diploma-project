package com.example.myapplication.dtos

import java.util.Date
import java.util.UUID

data class UserDTO(
    val userId: UUID,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val about: String,
    val matchesPlayed: Int,
    val matchesWon: Int,
    val joinedAt: Date
)