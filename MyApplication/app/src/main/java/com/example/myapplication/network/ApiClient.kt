package com.example.myapplication.network

import android.util.Log
import com.example.myapplication.dtos.UserRankingDTO
import com.example.myapplication.dtos.UserStatsGetDTO
import com.example.myapplication.jwt.TokenManagerHolder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.myapplication.network.networkRequests.LoginRequest
import com.example.myapplication.network.networkRequests.RegistrationRequest
import com.example.myapplication.network.networkResponses.AuthResponse
import com.example.myapplication.network.networkResponses.ErrorResponse
import com.google.gson.Gson
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

        val authUrl: OpenApiAuthService = Retrofit.Builder()
            .baseUrl(AUTH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenApiAuthService::class.java)

        val baseUrl: OpenApiUserService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenApiUserService::class.java)
    }


    suspend fun login(email: String, password: String): Any {

        val response = authUrl.login(LoginRequest(email, password))

        if(response.isSuccessful){
            val authResponse: AuthResponse = getAuthResponse(response)
            TokenManagerHolder.tokenManager.saveJwtToken(authResponse.token)

            return authResponse
        }else{
            val errorResponse = getErrorResponse(response)
            if(errorResponse.status == 403){
                errorResponse.error = "Wrong Username or Password, try again."
            }
            Log.d("LOGIN", errorResponse.error)

            return errorResponse
        }
    }

    suspend fun register(firstName: String, lastName: String, email: String, password: String): Any {

        val response = authUrl.register(RegistrationRequest(firstName, lastName, email, password, "USER"))

        if(response.isSuccessful){
            val authResponse: AuthResponse = getAuthResponse(response)
            tokenManager.saveJwtToken(authResponse.token)
            Log.d("REGISTER", "fine")

            return authResponse
            }else{
                val errorResponse: ErrorResponse = getErrorResponse(response)
                Log.d("REGISTER", "Response body is null")
                return errorResponse
            }

    }

    suspend fun getUser(email: String?, timePeriodOfActivities: Int?): UserStatsGetDTO? {

        Log.d("Token", tokenManager.getJwtToken().toString())

        val response = baseUrl.getUser(email,
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

    suspend fun getRanking(pageNumber: Int, pageSize: Int, sortDirection: String): List<UserRankingDTO>?{

        val response = baseUrl.getRanking(pageSize, pageNumber, sortDirection,"Bearer " +
                tokenManager.getJwtToken().toString())

        if(response.isSuccessful){
            val usersList = response.body()
            if(usersList != null){
                return usersList
            }else{
                Log.d("RankingGet", "Response body is null")
            }
        }else{
            Log.d("RankingGet", "Unsuccesful")
        }
        return null

    }

    private fun getErrorResponse(response: Response<*>): ErrorResponse{
        val gson = Gson()
        val errorBody = response.errorBody()?.string()

        return gson.fromJson(errorBody, ErrorResponse::class.java)
    }

    private fun getAuthResponse(response: Response<*>): AuthResponse{
        val gson = Gson()

        return gson.fromJson(gson.toJson(response.body()), AuthResponse::class.java)
    }
}