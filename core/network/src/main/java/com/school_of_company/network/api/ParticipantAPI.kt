package com.school_of_company.network.api

import com.school_of_company.network.dto.participant.response.ParticipantInformationResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ParticipantAPI {

    @GET("/participant/{expo_id}")
    suspend fun getParticipantInformationList(
        @Query("type") type: String,
        @Path("expo_id") expoId: String
    ) : List<ParticipantInformationResponse>
}