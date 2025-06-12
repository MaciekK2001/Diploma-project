package com.example.myapplication.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dtos.ActivityCreateDTO
import com.example.myapplication.model.Activity
import com.example.myapplication.network.ApiClient
import com.example.myapplication.network.networkResponses.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddActivitiesViewModel: ViewModel() {

    private val _apiResponse = MutableStateFlow<ApiResponse<Activity>?>(null)
    val apiResponse: StateFlow<ApiResponse<Activity>?> = _apiResponse.asStateFlow()

    fun addActivity(activityCreateDTO: ActivityCreateDTO){
        viewModelScope.launch(Dispatchers.IO) {
            _apiResponse.value = ApiClient.api.createActivity(activityCreateDTO)
            Log.d("apicall", _apiResponse.value.toString())
        }
    }

    fun updateActivity(activityCreateDTO: ActivityCreateDTO, activityId: String){
        viewModelScope.launch(Dispatchers.IO) {
            _apiResponse.value = ApiClient.api.updateActivity(activityCreateDTO, activityId)
        }
    }
}