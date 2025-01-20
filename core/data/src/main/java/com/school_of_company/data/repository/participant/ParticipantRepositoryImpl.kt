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
        type: String,
        expoId: String,
        name: String?
    ): Flow<List<ParticipantInformationResponseEntity>> {
        return dataSource.getParticipantInformationList(
            type = type,
            expoId = expoId,
            name = name
        ).transform { list ->
            emit(list.map { it.toEntity() })
        }
    }
}