package com.school_of_company.data.repository.expo

import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel
import com.school_of_company.network.datasource.expo.ExpoDataSource
import com.school_of_company.network.mapper.expo.request.toDto
import com.school_of_company.network.mapper.expo.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class ExpoRepositoryImpl @Inject constructor(
    private val dataSource: ExpoDataSource
) : ExpoRepository {
    override fun getExpoInformation(expoId: Long): Flow<ExpoRequestAndResponseModel> {
        return dataSource.getExpoInformation(expoId = expoId).transform { response ->
            emit(response.toModel())
        }
    }

    override fun registerExpoInformation(body: ExpoRequestAndResponseModel): Flow<Unit> {
        return dataSource.registerExpoInformation(body = body.toDto())
    }

    override fun modifyExpoInformation(
        expoId: Long,
        body: ExpoRequestAndResponseModel
    ): Flow<Unit> {
        return dataSource.modifyExpoInformation(
            expoId = expoId,
            body = body.toDto()
        )
    }

    override fun deleteExpoInformation(expoId: Long): Flow<Unit> {
        return dataSource.deleteExpoInformation(expoId = expoId)
    }
}