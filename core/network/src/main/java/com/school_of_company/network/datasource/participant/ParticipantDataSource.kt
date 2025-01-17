package com.school_of_company.network.datasource.participant

import com.school_of_company.network.dto.participant.response.ParticipantInformationResponse
import kotlinx.coroutines.flow.Flow

interface ParticipantDataSource {
    fun getParticipantInformationList(type: String, expoId: String): Flow<List<ParticipantInformationResponse>>
}