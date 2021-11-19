package com.example.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.assignment.api.ApiService
import com.example.assignment.api.CHARACTER_LIST_URL
import com.example.assignment.models.ApiResponse
import com.example.assignment.utils.geNetworkError
import kotlinx.coroutines.Dispatchers

/**
 * This class is used to handle dashboard view operation
 */
class DashboardViewModel : ViewModel() {

    /**
     * Reference for pagination url
     */
    private var mNextPageUrl: String? = CHARACTER_LIST_URL

    /**
     * This method is used to get next page url of character list
     * @return url name for pagination purpose
     */
    fun getNextPageUrl() = mNextPageUrl

    /**
     * This method is used to character list from server
     * @param pageUrl page url to get character list
     */
    fun getCharacterList(pageUrl: String) =
        liveData(Dispatchers.IO) {
            try {
                emit(ApiResponse.OnSuccess(ApiService.getCharacterList(pageUrl)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(geNetworkError(e))
            }
        }

    /**
     * This method is used to set next page url for pagination purpose
     */
    fun setNextPageUrl(pageUrl: String?) {
        mNextPageUrl = pageUrl
    }
}