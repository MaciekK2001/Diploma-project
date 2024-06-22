package com.example.myapplication.network

import com.example.myapplication.network.networkResponses.LoginResponse
import com.example.myapplication.network.networkResponses.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class LoginRequest(
    val username: String,
    val password: String
)

data class RegistrationRequest(
    val email: String,
    val username: String,
    val password: String,
    val password2: String
)

interface OpenApiAuthService {
    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun register(
        @Body registrationRequest: RegistrationRequest
    ): RegistrationResponse
}
