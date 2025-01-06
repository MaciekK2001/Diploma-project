package com.example.myapplication.dtos

import com.example.myapplication.model.Activity

data class AppUserBasicDataDTO(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val lastActivity: Activity? = null,
    val username: String? = null
)