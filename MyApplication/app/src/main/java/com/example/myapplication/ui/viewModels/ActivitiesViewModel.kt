package com.example.myapplication.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Activity
import com.example.myapplication.model.ActivityType
import com.example.myapplication.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActivitiesViewModel : ViewModel() {

    private val _listOfActivities = MutableStateFlow<List<Activity>>(emptyList())
    val listOfActivities: StateFlow<List<Activity>> = _listOfActivities

    private val _searchParameters = MutableStateFlow(SearchParameters())

    private var currentPage = 0
    private var pageSize = 5
    private var userId: String? = null


    fun loadNextPage() {
        viewModelScope.launch {
            if (userId != null) {
                val activitiesList = ApiClient.api.getActivities(
                    userId!!, currentPage, pageSize,
                    _searchParameters.value.sortDirection, _searchParameters.value.sortBy,
                    _searchParameters.value.selectedActivityTypes
                )
                if (activitiesList != null) {
                    _listOfActivities.value += activitiesList
                    currentPage++
                }
            } else {
                val activitiesList = ApiClient.api.getActivitiesToken(
                    currentPage, pageSize,
                    _searchParameters.value.sortDirection, _searchParameters.value.sortBy,
                    _searchParameters.value.selectedActivityTypes
                )
                if (activitiesList != null) {
                    _listOfActivities.value += activitiesList
                    currentPage++
                }
            }
        }
    }

    fun deleteActivity(activityId: String){
        viewModelScope.launch {
            ApiClient.api.deleteActivity(activityId)
        }
    }


    fun updateSearchParameters(parameters: SearchParameters) {
        _searchParameters.value = parameters
        this.currentPage = 0
    }

    fun searchClicked() {
        this.currentPage = 0
        _listOfActivities.value = emptyList()
        loadNextPage()
    }

    data class SearchParameters(
        var selectedActivityTypes: List<ActivityType> = emptyList(),
        var sortDirection: String? = null,
        var sortBy: String? = null
    )

    fun setUserId(userId: String) {
        this.userId = userId
    }
}