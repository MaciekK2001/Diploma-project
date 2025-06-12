package com.example.myapplication.model

import com.example.myapplication.dtos.DailyStatisticsDTO
import com.example.myapplication.dtos.MonthlyStatisticsDTO
import com.example.myapplication.dtos.StatisticsPeriod
import com.example.myapplication.dtos.StatisticsSearchParams
import com.example.myapplication.network.ApiClient
import com.example.myapplication.network.networkResponses.ApiResponse

class StatisticsRepository {
    suspend fun getDailyStatistics(statisticsSearchParams: StatisticsSearchParams) : ApiResponse<List<DailyStatisticsDTO>>{
        validateSearchParams(statisticsSearchParams)
        return ApiClient.api.getDailyStatistics(statisticsSearchParams)
    }

    suspend fun getYearlyStatistics() : ApiResponse<List<MonthlyStatisticsDTO>>{
        return ApiClient.api.getYearlyStatistics()
    }

    private fun validateSearchParams(params: StatisticsSearchParams) {
        when (params.statisticsPeriod) {
            StatisticsPeriod.WEEK, StatisticsPeriod.MONTH -> {
            }
            StatisticsPeriod.YEAR -> {
                throw IllegalArgumentException("Invalid period for daily statistics: YEAR is not allowed.")
            }
        }
    }
}