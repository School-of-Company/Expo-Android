package com.school_of_company.data.repository.participant

import com.school_of_company.model.entity.participant.ParticipantInformationResponseEntity
import kotlinx.coroutines.flow.Flow

interface ParticipantRepository {
    fun getParticipantInformationList(
        expoId: String,
        page: Int? = null,
        size: Int? = null,
        localDate: String? = null
    ): Flow<ParticipantInformationResponseEntity>
}