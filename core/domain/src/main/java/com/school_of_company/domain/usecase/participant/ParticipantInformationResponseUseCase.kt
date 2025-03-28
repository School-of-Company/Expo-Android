package com.school_of_company.domain.usecase.participant

import com.school_of_company.data.repository.participant.ParticipantRepository
import com.school_of_company.model.entity.participant.ParticipantInformationResponseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ParticipantInformationResponseUseCase @Inject constructor(
    private val repository: ParticipantRepository
) {
    operator fun invoke(
        type: String,
        expoId: String,
        name: String? = null,
        page: Int? = null,
        size: Int? = null,
        localDate: String? = null
    ): Flow<ParticipantInformationResponseEntity> =
        repository.getParticipantInformationList(
            type = type,
            expoId = expoId,
            name = name,
            page = page,
            size = size,
            localDate = localDate
        )
}