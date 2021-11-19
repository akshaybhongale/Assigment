package com.example.assignment.models

/**
 * This class is used handle state of REST api call
 */
sealed class ApiResponse<out T> {
    /**
     * This state is called when REST call api is executed successfully
     * @param result response from server
     */
    data class OnSuccess<out T>(val result: T) : ApiResponse<T>()

    /**
     * This state is called when we are getting any error while executing REST api call
     * @param errorCode error code
     * @param errorMsg error message to display
     */
    data class OnError(val errorCode: Int, val errorMsg: String) : ApiResponse<Nothing>()
}
