package com.example.myapplication.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dtos.ChangePasswordDTO
import com.example.myapplication.dtos.MessageResponseDTO
import com.example.myapplication.network.ApiClient
import com.example.myapplication.network.networkResponses.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChangePasswordViewModel : ViewModel() {
    private val _changeResponse = MutableStateFlow<ApiResponse<MessageResponseDTO>?>(null)
    val changeResponse: StateFlow<ApiResponse<MessageResponseDTO>?> = _changeResponse.asStateFlow()

    fun changePassword(changePasswordDTO: ChangePasswordDTO) {
        _changeResponse.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val response = ApiClient.api.changePassword(changePasswordDTO)
            _changeResponse.value = response
        }
    }
}