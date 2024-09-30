package com.example.myapplication.network.networkResponses

import com.google.gson.annotations.SerializedName

data class AuthResponse(

    @SerializedName("message")
    var message: String,

    @SerializedName("token")
    var token: String

)