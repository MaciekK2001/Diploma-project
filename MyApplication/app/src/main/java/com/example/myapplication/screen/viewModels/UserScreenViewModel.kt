package com.example.myapplication.screen.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dtos.UserStatsGetDTO
import com.example.myapplication.network.ApiClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserScreenViewModel : ViewModel() {

    private val _userStatsGetDTO = MutableStateFlow(
        UserStatsGetDTO(null, 0, null, null)
    )
    val userStatsGetDTO: StateFlow<UserStatsGetDTO> = _userStatsGetDTO.asStateFlow()

    private val _isUserLoaded = MutableStateFlow(false)
    val isUserLoaded: StateFlow<Boolean> = _isUserLoaded.asStateFlow()

    init {
        getUser()
    }
    private fun getUser(){
        viewModelScope.launch {
            delay(2000) //TEST
            val responseUserStatsGetDTO: UserStatsGetDTO? =
                ApiClient.api.getUser("", null)

            if(responseUserStatsGetDTO != null){
                _userStatsGetDTO.update { currentState ->
                    val newState = currentState.copy(
                        appUser = responseUserStatsGetDTO.appUser,
                        avgCalories = responseUserStatsGetDTO.avgCalories,
                        favActivityType = responseUserStatsGetDTO.favActivityType,
                        lastActivity = responseUserStatsGetDTO.lastActivity
                    )
                    newState
                }
                _isUserLoaded.value = true
            }
        }

    }

}