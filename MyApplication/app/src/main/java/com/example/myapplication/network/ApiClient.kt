package com.example.myapplication.network

import android.util.Log
import com.example.myapplication.dtos.ActivityCreateDTO
import com.example.myapplication.dtos.ChangePasswordDTO
import com.example.myapplication.dtos.DailyStatisticsDTO
import com.example.myapplication.dtos.MessageResponseDTO
import com.example.myapplication.dtos.MonthlyStatisticsDTO
import com.example.myapplication.dtos.StatisticsSearchParams
import com.example.myapplication.dtos.UserBasicDataDTO
import com.example.myapplication.dtos.UserRankingDTO
import com.example.myapplication.dtos.UserStatsGetDTO
import com.example.myapplication.security.TokenManagerHolder
import com.example.myapplication.model.Activity
import com.example.myapplication.model.ActivityType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.myapplication.network.networkRequests.LoginRequest
import com.example.myapplication.network.networkRequests.RegistrationRequest
import com.example.myapplication.network.networkResponses.ApiResponse
import com.example.myapplication.network.networkResponses.AuthResponse
import com.example.myapplication.network.networkResponses.makeApiCall


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
        val tokenManager = TokenManagerHolder.retrieveTokenManager()

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


    suspend fun login(email: String, password: String): ApiResponse<AuthResponse> {

        val response =  makeApiCall { authUrl.login(LoginRequest(email, password)) }
        if(response is ApiResponse.Success) {
            TokenManagerHolder.tokenManager.saveJwtToken(response.data.token)
        }
        return response
    }

    suspend fun register(registrationRequest: RegistrationRequest): ApiResponse<AuthResponse> {

        val response =  makeApiCall { authUrl.register(registrationRequest) }
        if(response is ApiResponse.Success) {
            TokenManagerHolder.tokenManager.saveJwtToken(response.data.token)
        }
        return response

    }

    suspend fun getUser(username: String?, timePeriodOfActivities: Int?): UserStatsGetDTO? {

        Log.d("Token", tokenManager.getJwtToken().toString())

        val response = baseUrl.getUser(username,
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

    suspend fun getActivities(userId: String, pageNumber: Int, pageSize:Int, sortDirection: String?, sortBy: String?,
                              conditions: List<ActivityType>?): List<Activity>? {

        val response = baseUrl.getActivities(userId, pageSize, pageNumber, sortDirection, sortBy,
            conditions,"Bearer " + tokenManager.getJwtToken().toString())

        if(response.isSuccessful){
            val activitiesList = response.body()
            if(activitiesList != null){
                return activitiesList
            }else{
                Log.d("ActivitiesGet", "Response body is null")
            }
        }else{
            Log.d("ActivitiesGet", "Unsuccesful")
        }
        return null
    }

    suspend fun getActivitiesToken(pageNumber: Int, pageSize:Int, sortDirection: String?, sortBy: String?,
                              conditions: List<ActivityType>?): List<Activity>? {

        val response = baseUrl.getActivitiesToken(pageSize, pageNumber, sortDirection, sortBy,
            conditions,"Bearer " + tokenManager.getJwtToken().toString())

        if(response.isSuccessful){
            val activitiesList = response.body()
            if(activitiesList != null){
                return activitiesList
            }else{
                Log.d("ActivitiesGet", "Response body is null")
            }
        }else{
            Log.d("ActivitiesGet", "Unsuccesful")
        }
        return null

    }

    suspend fun getUserBasicData(): UserBasicDataDTO?{
        val response = baseUrl.getUserBasicData("Bearer " + tokenManager.getJwtToken().toString())

        if(response.isSuccessful){
            val userBasicDataDTO = response.body()
            return userBasicDataDTO
        } else{
            return null
        }
    }

    suspend fun createActivity(activityCreateDTO: ActivityCreateDTO): ApiResponse<Activity>{
        return makeApiCall {
            baseUrl.createActivity(
                "Bearer " + tokenManager.getJwtToken().toString(),
                activityCreateDTO
            )
        }
    }

    suspend fun updateActivity(activityCreateDTO: ActivityCreateDTO, activityId: String): ApiResponse<Activity>{
        return makeApiCall {
            baseUrl.updateActivity(
                "Bearer " + tokenManager.getJwtToken().toString(), activityId,
                activityCreateDTO
            )
        }

    }

    suspend fun deleteActivity(activityId: String): ApiResponse<Int>{
        return makeApiCall {
            baseUrl.deleteActivity("Bearer " + tokenManager.getJwtToken().toString(), activityId)
        }

    }

    suspend fun changePassword(changePasswordDTO: ChangePasswordDTO): ApiResponse<MessageResponseDTO>{
        return makeApiCall {
            baseUrl.changePassword("Bearer " + tokenManager.getJwtToken().toString(), changePasswordDTO)
        }
    }

    suspend fun logout(): ApiResponse<MessageResponseDTO>{
        return makeApiCall {
            baseUrl.logout("Bearer " + tokenManager.getJwtToken().toString())
        }
    }

    suspend fun getDailyStatistics(statisticsSearchParams: StatisticsSearchParams): ApiResponse<List<DailyStatisticsDTO>>{
        return makeApiCall {
            baseUrl.getDailyStatistics("Bearer " + tokenManager.getJwtToken().toString(), statisticsSearchParams)
        }
    }

    suspend fun getYearlyStatistics(): ApiResponse<List<MonthlyStatisticsDTO>>{
        return makeApiCall {
            baseUrl.getYearlyStatistics("Bearer " + tokenManager.getJwtToken().toString())
        }
    }
}