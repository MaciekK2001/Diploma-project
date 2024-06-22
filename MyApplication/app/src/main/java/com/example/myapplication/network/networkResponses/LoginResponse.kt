package com.example.myapplication.network.networkResponses

import com.google.gson.annotations.SerializedName

class LoginResponse(

    @SerializedName("response")
    var response: String,

    @SerializedName("error_message")
    var errorMessage: String?,

    @SerializedName("token")
    var token: String,

)