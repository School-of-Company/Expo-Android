package com.school_of_company.network.datasource.participant

import com.school_of_company.network.api.ParticipantAPI
import com.school_of_company.network.dto.participant.response.ParticipantInformationResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ParticipantDataSourceImpl @Inject constructor(
    private val service: ParticipantAPI
) : ParticipantDataSource {
    override fun getParticipantInformationList(
        type: String,
        expoId: String,
        name: String?,
        page: Int?,
        size: Int?,
        localDate: String?
    ): Flow<ParticipantInformationResponse> =
        performApiRequest { service.getParticipantInformationList(
            type = type,
            expoId = expoId,
            name = name,
            page = page,
            size = size,
            localDate = localDate
        ) }
}