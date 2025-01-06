package com.example.myapplication.network.networkResponses

import com.google.gson.annotations.SerializedName

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

suspend fun <T> makeApiCall(
    call: suspend () -> retrofit2.Response<T>
): ApiResponse<T> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                ApiResponse.Success(body)
            } else {
                @Suppress("UNCHECKED_CAST")
                ApiResponse.Success(response.code() as T)
            }
        } else {
            ApiResponse.Error(response.message(), response.code())
        }
    } catch (e: Exception) {
        ApiResponse.Error("Network error: ${e.localizedMessage}")
    }
}
