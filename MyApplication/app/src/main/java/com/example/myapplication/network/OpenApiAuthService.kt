package com.example.myapplication.network

import com.example.myapplication.dtos.UserStatsGetDTO
import com.example.myapplication.network.networkRequests.LoginRequest
import com.example.myapplication.network.networkRequests.RegistrationRequest
import com.example.myapplication.network.networkResponses.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface OpenApiAuthService {
    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<AuthResponse>

    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun register(
        @Body registrationRequest: RegistrationRequest
    ): Response<AuthResponse>

    @Headers("Content-Type: application/json")
    @GET("users")
    suspend fun getUser(
        @Query("email") email: String?,
        @Query("timePeriodOfAvtivities") timePeriodOfActivities: Int?,
        @Header("Authorization") token: String
    ): Response<UserStatsGetDTO>
}
