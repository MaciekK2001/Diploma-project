package com.example.myapplication.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dtos.DailyStatisticsDTO
import com.example.myapplication.dtos.MonthlyStatisticsDTO
import com.example.myapplication.dtos.StatisticsSearchParams
import com.example.myapplication.model.StatisticsRepository
import com.example.myapplication.network.networkResponses.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StatisticsViewModel : ViewModel() {
    private val statisticsRepository : StatisticsRepository = StatisticsRepository()

    private val _statisticsDailyResponse = MutableStateFlow<ApiResponse<List<DailyStatisticsDTO>>?>(null)
    val statisticsDailyResponse: StateFlow<ApiResponse<List<DailyStatisticsDTO>>?> = _statisticsDailyResponse.asStateFlow()

    private val _statisticsYearlyResponse = MutableStateFlow<ApiResponse<List<MonthlyStatisticsDTO>>?>(null)
    val statisticsYearlyResponse: StateFlow<ApiResponse<List<MonthlyStatisticsDTO>>?> = _statisticsYearlyResponse.asStateFlow()

    fun getDailyStatistics(statisticsSearchParams: StatisticsSearchParams){
        viewModelScope.launch(Dispatchers.IO) {
            _statisticsDailyResponse.value = statisticsRepository.getDailyStatistics(statisticsSearchParams)
        }
    }
    fun getYearlyStatistics(){
        viewModelScope.launch(Dispatchers.IO) {
            _statisticsYearlyResponse.value = statisticsRepository.getYearlyStatistics()
        }
    }
}