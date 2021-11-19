package com.example.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.assignment.api.ApiService
import com.example.assignment.models.ApiResponse
import com.example.assignment.utils.geNetworkError
import kotlinx.coroutines.Dispatchers

/**
 * This class is used to handle Character details view operations
 */
class CharacterDetailsViewModel : ViewModel() {

    /**
     * This method is used to get location details from server
     * @param url url to get location details
     */
    fun getLocationDetails(url: String?) = liveData(Dispatchers.IO) {
        try {
            emit(ApiResponse.OnSuccess(ApiService.getLocationDetails(url)))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(geNetworkError(e))
        }
    }
}