package com.school_of_company.network.api

import com.school_of_company.network.dto.expo.request_response.ExpoRequestAndResponse
import com.school_of_company.network.dto.expo.response.ExpoListResponse
import retrofit2.http.*

interface ExpoAPI {

    @GET("/expo")
    suspend fun getExpoList(): List<ExpoListResponse>

    @GET("/expo/{expo_id}")
    suspend fun getExpoInformation(
        @Path("expo_id") expoId: Long
    ) : ExpoRequestAndResponse

    @POST("/expo")
    suspend fun registerExpoInformation(
        @Body body: ExpoRequestAndResponse
    )

    @PATCH("/expo/{expo_id}")
    suspend fun modifyExpoInformation(
        @Path("expo_id") expoId: Long,
        @Body body: ExpoRequestAndResponse
    )

    @DELETE("/expo/{expo_id}")
    suspend fun deleteExpoInformation(
        @Path("expo_id") expoId: Long
    )
}