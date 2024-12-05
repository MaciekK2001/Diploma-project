package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class User (
    @PrimaryKey val userId: UUID,
    val firstName: String,
    val lastName: String,
    val about: String,
    val email: String,
    val joinedAt: Date
)

