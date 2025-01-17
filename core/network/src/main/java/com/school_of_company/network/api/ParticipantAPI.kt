package com.school_of_company.network.api

import com.school_of_company.network.dto.participant.response.ParticipantInformationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ParticipantAPI {

    @GET("/participant")
    suspend fun getParticipantInformationList(
        @Query("type") type: String
    ) : List<ParticipantInformationResponse>
}