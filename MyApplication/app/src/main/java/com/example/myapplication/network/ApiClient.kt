package com.example.myapplication.network

import android.util.Log
import com.example.myapplication.dtos.UserStatsGetDTO
import com.example.myapplication.jwt.TokenManagerHolder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.myapplication.network.networkRequests.LoginRequest
import com.example.myapplication.network.networkRequests.RegistrationRequest
import retrofit2.Response

class ApiClient {
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

        val api = ApiClient()
        val tokenManager = TokenManagerHolder.retriveTokenManager()
    }


    suspend fun login(email: String, password: String): Boolean {

        val api = Retrofit.Builder()
            .baseUrl(AUTH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenApiAuthService::class.java);


        val response = api.login(LoginRequest(email, password))

        if(response.isSuccessful){
            val authResponse = response.body()
            if(authResponse != null){
                tokenManager.saveJwtToken(authResponse.token)
                Log.d("LOGIN", "fine")
                return true
            }else{
                Log.d("LOGIN", "Response body is null")
                return false
            }
        }else{
            Log.d("LOGIN", "Unsuccesful")
        }

        return false
    }

    suspend fun register(firstName: String, lastName: String, email: String, password: String): Boolean {

        val api = Retrofit.Builder()
            .baseUrl(AUTH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenApiAuthService::class.java);

        val response = api.register(RegistrationRequest(firstName, lastName, email, password, "USER"))

        if(response.isSuccessful){
            val authResponse = response.body()
            if(authResponse != null){
                tokenManager.saveJwtToken(authResponse.token)
                Log.d("REGISTER", "fine")
                return true
            }else{
                Log.d("REGISTER", "Response body is null")
                return false
            }
        }else{
            Log.d("REGISTER", "Unsuccesful")
        }

        return false
    }

    suspend fun getUser(email: String?, timePeriodOfActivities: Int?): UserStatsGetDTO? {

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenApiAuthService::class.java)

        Log.d("Token", tokenManager.getJwtToken().toString())

        val response = api.getUser(email,
            timePeriodOfActivities,
            "Bearer " +
            tokenManager.getJwtToken().toString()
        )

        if(response.isSuccessful){
            val userStatsGetDTO = response.body()
            if(userStatsGetDTO != null){
                return userStatsGetDTO
            }else{
                Log.d("USERGET", "Response body is null")
            }
        }else{
            Log.d("USERGET", "Unsuccesful")
        }
        return null
    }


}