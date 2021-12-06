package com.example.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.assignment.api.ApiService
import com.example.assignment.api.CHARACTER_LIST_URL
import com.example.assignment.models.ApiResponse
import com.example.assignment.utils.EMPTY_STRING
import com.example.assignment.utils.ERROR_CODE_404
import com.example.assignment.utils.ERROR_MSG_404
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


    private var mSearchString: String? = EMPTY_STRING

    /**
     * This method is used to get next page url of character list
     * @return url name for pagination purpose
     */
    fun getNextPageUrl() = mNextPageUrl

    /**
     * This method is used to character list from server
     * @param pageUrl page url to get character list
     */
    fun getCharacterList(pageUrl: String, searchString: String?) =
        liveData(Dispatchers.IO) {
            try {
                val result = ApiService.getCharacterList(pageUrl, searchString)
                if (result.error == null) {
                    emit(ApiResponse.OnSuccess(result))
                } else {
                    emit(ApiResponse.OnError(ERROR_CODE_404, result.error ?: ERROR_MSG_404))
                }
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

    /**
     * This method is used to maintain search string for pagination purpose
     * @param searchString search string from UI
     */
    fun setSearchString(searchString: String?) {
        mSearchString = searchString
    }

    /**
     * This method is used to get Search string
     */
    fun getSearchString() = mSearchString

    /**
     * This method is used to set default value for searchString and next page url
     */
    fun clear(){
        mSearchString = EMPTY_STRING
        mNextPageUrl = CHARACTER_LIST_URL
    }
}