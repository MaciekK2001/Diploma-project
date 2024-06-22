package com.example.myapplication.network

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val TAG = "MyActivity"
    init {
        getAuthRetrofitInstance();
        getBaseRetrofitInstance();
    }

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/api/"
        private const val AUTH_URL = "http://10.0.2.2:8080/auth/"

        private var retrofitInstanceAuth: Retrofit? = null
        private var retrofitInstanceBase: Retrofit? = null

        fun getAuthRetrofitInstance() {
            if (retrofitInstanceAuth == null) {
                retrofitInstanceAuth = Retrofit.Builder()
                    .baseUrl(AUTH_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
        }

        fun getBaseRetrofitInstance() {
            if (retrofitInstanceBase == null) {
                retrofitInstanceBase = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
        }
    }


    suspend fun login(username: String, password: String){


        Log.d(TAG, username + password)
        val api = Retrofit.Builder()
            .baseUrl(AUTH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenApiAuthService::class.java);

        api.login(LoginRequest(username, password))
    }


}