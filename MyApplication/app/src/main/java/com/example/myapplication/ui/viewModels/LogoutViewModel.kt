package com.example.myapplication.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dtos.MessageResponseDTO
import com.example.myapplication.network.ApiClient
import com.example.myapplication.network.networkResponses.ApiResponse
import com.example.myapplication.security.TokenManagerHolder
import com.example.myapplication.security.UserDataHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LogoutViewModel : ViewModel() {
    private val _logoutResponse = MutableStateFlow<ApiResponse<MessageResponseDTO>?>(null)
    val logoutResponse: StateFlow<ApiResponse<MessageResponseDTO>?> = _logoutResponse.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            val response = ApiClient.api.logout()
            _logoutResponse.value = response

            if (response is ApiResponse.Success) {
                UserDataHolder.userData.clearUserData()
                TokenManagerHolder.tokenManager.deleteJwtToken()
            }
        }
    }
}