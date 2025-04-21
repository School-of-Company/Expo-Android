package com.school_of_company.network.datasource.participant

import com.school_of_company.network.dto.participant.response.ParticipantInformationResponse
import kotlinx.coroutines.flow.Flow

interface ParticipantDataSource {
    fun getParticipantInformationList(
        expoId: String,
        page: Int? = null,
        size: Int? = null,
        localDate: String? = null
    ): Flow<ParticipantInformationResponse>
}