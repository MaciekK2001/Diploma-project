package com.example.myapplication.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dtos.UserRankingDTO
import com.example.myapplication.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsersRankingViewModel() : ViewModel() {

    private val _listOfUsers = MutableStateFlow<List<UserRankingDTO>>(emptyList())
    val listOfUsers: StateFlow<List<UserRankingDTO>> = _listOfUsers

    private var currentPage = 0
    private var pageSize = 5
    private var sortDirection = "DESC"

    fun loadNextPage() {
        viewModelScope.launch {
            val usersList = ApiClient.api.getRanking(currentPage, pageSize, sortDirection)
            if (usersList != null) {
                _listOfUsers.value += usersList
                currentPage++
            }
        }
    }

}
