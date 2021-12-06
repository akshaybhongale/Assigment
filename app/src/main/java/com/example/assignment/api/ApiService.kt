package com.example.assignment.api

import com.example.assignment.models.CharacterList
import com.example.assignment.models.Location
import com.example.assignment.utils.API_TIME_OUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * This class is used to create REST API client
 * and execute REST API call and handle JSON parsing
 */
object ApiService {

    /**
     * Instance variable to build and execute REST API Call
     */
    private val mIApi by lazy {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitBuilder.create(IApi::class.java)
    }

    /**
     * HttpClient for debugging and customizing network request
     */
    private val mHttpClient by lazy {

        OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(API_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(API_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(API_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    /**
     * This method is used to get character list from server
     * @param pageUrl pageUrl reference for pagination
     * @return CharacterList response from server
     */
    suspend fun getCharacterList(pageUrl: String,searchString: String?): CharacterList {
        return mIApi.getCharacter(pageUrl,searchString)
    }

    /**
     * This method is used to get location details from server
     * @param locationUrl reference url get location details
     * @return Location response from server
     */
    suspend fun getLocationDetails(locationUrl: String?): Location {
        return mIApi.getLocation(locationUrl)
    }
}