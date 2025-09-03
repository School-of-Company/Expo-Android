package com.school_of_company.data.repository.participant

import com.school_of_company.model.entity.participant.ParticipantInformationResponseEntity
import com.school_of_company.network.datasource.participant.ParticipantDataSource
import com.school_of_company.network.mapper.participant.response.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class ParticipantRepositoryImpl @Inject constructor(
    private val dataSource: ParticipantDataSource
) : ParticipantRepository {
    override fun getParticipantInformationList(
        expoId: String,
        page: Int?,
        size: Int?,
        localDate: String?
    ): Flow<ParticipantInformationResponseEntity> {
        return dataSource.getParticipantInformationList(
            expoId = expoId,
            page = page,
            size = size,
            localDate = localDate
        ).transform { response ->
            emit(response.toEntity())
        }
    }
}