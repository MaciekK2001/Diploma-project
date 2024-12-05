package com.example.myapplication.network

import com.example.myapplication.dtos.UserRankingDTO
import com.example.myapplication.dtos.UserStatsGetDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface OpenApiUserService {
    @Headers("Content-Type: application/json")
    @GET("users")
    suspend fun getUser(
        @Query("email") email: String?,
        @Query("timePeriodOfAvtivities") timePeriodOfActivities: Int?,
        @Header("Authorization") token: String
    ): Response<UserStatsGetDTO>

    @Headers("Content-Type: application/json")
    @GET("users/ranking")
    suspend fun getRanking(
        @Query("pageSize") pageSize: Int?,
        @Query("pageNumber") pageNumber: Int?,
        @Query("sortDirection") sortDirection: String?,
        @Header("Authorization") token: String?//DESC || ASC
    ): Response<List<UserRankingDTO>>
}