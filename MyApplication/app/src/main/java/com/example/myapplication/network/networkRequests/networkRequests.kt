package com.example.myapplication.network.networkRequests

data class LoginRequest(
    val username: String,
    val password: String
)

data class RegistrationRequest(
    val firstName: String,
    val lastName: String,
    val username: String,
    val password: String,
    val email: String,
    val role: String
)

data class UserStatsRequest(
    val username: String,
    val timePeriodOfActivities: Int
)
