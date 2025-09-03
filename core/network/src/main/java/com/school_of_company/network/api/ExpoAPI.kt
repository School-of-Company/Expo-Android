package com.school_of_company.network.api

import com.school_of_company.network.dto.expo.request.ExpoAllRequest
import com.school_of_company.network.dto.expo.request.ExpoModifyRequest
import com.school_of_company.network.dto.expo.response.ExpoResponse
import com.school_of_company.network.dto.expo.response.ExpoIdResponse
import com.school_of_company.network.dto.expo.response.ExpoListResponse
import com.school_of_company.network.dto.expo.response.ExpoSurveyDynamicFormEnabledResponse
import retrofit2.http.*

interface ExpoAPI {

    @GET("/expo")
    suspend fun getExpoList(): List<ExpoListResponse>

    @GET("/expo/{expo_id}")
    suspend fun getExpoInformation(
        @Path("expo_id") expoId: String
    ) : ExpoResponse

    @POST("/expo")
    suspend fun registerExpoInformation(
        @Body body: ExpoAllRequest
    ) : ExpoIdResponse

    @PATCH("/expo/{expo_id}")
    suspend fun modifyExpoInformation(
        @Path("expo_id") expoId: String,
        @Body body: ExpoModifyRequest
    )

    @DELETE("/expo/{expo_id}")
    suspend fun deleteExpoInformation(
        @Path("expo_id") expoId: String
    )

    @GET("/expo/valid")
    suspend fun checkExpoSurveyDynamicFormEnable() : ExpoSurveyDynamicFormEnabledResponse
}