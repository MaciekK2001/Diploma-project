package com.example.myapplication.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dtos.UserStatsGetDTO
import com.example.myapplication.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _userStatsGetDTO = MutableStateFlow(
        UserStatsGetDTO(null, 0, null, null)
    )
    val userStatsGetDTO: StateFlow<UserStatsGetDTO> = _userStatsGetDTO.asStateFlow()

    private val _isUserLoaded = MutableStateFlow(false)
    val isUserLoaded: StateFlow<Boolean> = _isUserLoaded.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            val responseUserStatsGetDTO: UserStatsGetDTO? =
                ApiClient.api.getUser("", null)

            if (responseUserStatsGetDTO != null) {
                _userStatsGetDTO.value = responseUserStatsGetDTO
                _isUserLoaded.value = true
            }
        }
    }

    fun getOtherUser(username: String) {
        viewModelScope.launch {
            val responseUserStatsGetDTO: UserStatsGetDTO? =
                ApiClient.api.getUser(username, null)

            if (responseUserStatsGetDTO != null) {
                _userStatsGetDTO.value = responseUserStatsGetDTO
                _isUserLoaded.value = true
            }
        }

    }
}