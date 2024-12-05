package com.example.myapplication.dtos

data class PaginatedResponse<T>(
    val list: List<T>,
)