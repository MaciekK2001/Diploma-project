package com.example.myapplication.network

import com.example.myapplication.dtos.ActivityCreateDTO
import com.example.myapplication.dtos.UserBasicDataDTO
import com.example.myapplication.dtos.UserRankingDTO
import com.example.myapplication.dtos.UserStatsGetDTO
import com.example.myapplication.model.Activity
import com.example.myapplication.model.ActivityType
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
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
        @Header("Authorization") token: String
    ): Response<List<UserRankingDTO>>

    @Headers("Content-Type: application/json")
    @GET("activities/{userId}")
    @JvmSuppressWildcards
    suspend fun getActivities(
        @Path("userId") userId: String,
        @Query("pageSize") pageSize: Int?,
        @Query("pageNumber") pageNumber: Int?,
        @Query("sortOrder") sortOrder: String?,
        @Query("sortBy") sortBy: String?,
        @Query("conditions") conditions: List<ActivityType>?,
        @Header("Authorization") token: String//DESC || ASC
    ): Response<List<Activity>>

    @Headers("Content-Type: application/json")
    @GET("activities")
    @JvmSuppressWildcards
    suspend fun getActivitiesToken(
        @Query("pageSize") pageSize: Int?,
        @Query("pageNumber") pageNumber: Int?,
        @Query("sortOrder") sortOrder: String?,
        @Query("sortBy") sortBy: String?,
        @Query("conditions") conditions: List<ActivityType>?,
        @Header("Authorization") token: String
    ): Response<List<Activity>>

    @Headers("Content-Type: application/json")
    @GET("userBasicData")
    suspend fun getUserBasicData(
        @Header("Authorization") token: String
    ): Response<UserBasicDataDTO>

    @Headers("Content-Type: application/json")
    @POST("activities")
    suspend fun createActivity(
        @Header("Authorization") token: String,
        @Body activityCreateDTO: ActivityCreateDTO
    ): Response<Activity>

    @Headers("Content-Type: application/json")
    @PUT("activities/{activityId}")
    suspend fun updateActivity(
        @Header("Authorization") token: String,
        @Path("activityId") activityId: String,
        @Body activityCreateDTO: ActivityCreateDTO
    ): Response<Activity>

    @Headers("Content-Type: application/json")
    @DELETE("activities/{activityId}")
    suspend fun deleteActivity(
        @Header("Authorization") token: String,
        @Path("activityId") activityId: String,
    ): Response<Int>

}