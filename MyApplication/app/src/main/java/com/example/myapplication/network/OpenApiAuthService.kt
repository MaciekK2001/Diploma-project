package com.example.myapplication.network

import com.example.myapplication.network.networkRequests.LoginRequest
import com.example.myapplication.network.networkRequests.RegistrationRequest
import com.example.myapplication.network.networkResponses.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

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

}


