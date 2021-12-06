package com.example.assignment.api

import com.example.assignment.models.CharacterList
import com.example.assignment.models.Location
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Interface for consuming REST Api
 */
interface IApi {

    /**
     * This method is used to execute REST API call to get character list
     * @param pageUrl reference for pagination
     * @return CharacterList response from Server
     */
    @GET
    suspend fun getCharacter(@Url pageUrl: String,@Query(NAME) searchString: String?): CharacterList

    /**
     * This method is used to execute REST API call to get location details
     * @param locationUrl url to get details
     * @return Location response from Server
     */
    @GET
    suspend fun getLocation(@Url locationUrl: String?): Location

}