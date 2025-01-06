package com.example.myapplication.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.ApiClient
import com.example.myapplication.network.networkRequests.RegistrationRequest
import com.example.myapplication.network.networkResponses.ApiResponse
import com.example.myapplication.network.networkResponses.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {
    private val _registerResponse = MutableStateFlow<ApiResponse<AuthResponse>?>(null)
    val registerResponse: StateFlow<ApiResponse<AuthResponse>?> = _registerResponse.asStateFlow()

    fun register(registrationRequest: RegistrationRequest) {
        _registerResponse.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val response = ApiClient.api.register(registrationRequest)
            _registerResponse.value = response
        }
    }
}