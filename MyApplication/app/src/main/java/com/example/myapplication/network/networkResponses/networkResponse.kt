package com.example.myapplication.network.networkResponses

import com.google.gson.annotations.SerializedName
import retrofit2.Response

data class AuthResponse(

    @SerializedName("message")
    var message: String,

    @SerializedName("token")
    var token: String

)

data class ErrorResponse(
    @SerializedName("error")
    var error: String,

    @SerializedName("status")
    var status: Int
)

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResponse<Nothing>()
}

suspend fun <T> makeApiCall(call: suspend () -> Response<T>): ApiResponse<T> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            response.body()?.let { ApiResponse.Success(it) }
                ?: ApiResponse.Error("Empty response body", response.code())
        } else {
            val errorMessage = response.errorBody()?.string() ?: "Unknown error"
            ApiResponse.Error(errorMessage, response.code())
        }
    } catch (e: Exception) {
        ApiResponse.Error("Network error: ${e.localizedMessage}")
    }
}
