package com.school_of_company.network.datasource.expo

import com.school_of_company.network.api.ExpoAPI
import com.school_of_company.network.dto.expo.response.ExpoResponse
import com.school_of_company.network.dto.expo.response.ExpoIdResponse
import com.school_of_company.network.dto.expo.response.ExpoListResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpoDataSourceImpl @Inject constructor(
    private val service: ExpoAPI
) : ExpoDataSource {
    override fun getExpoList(): Flow<List<ExpoListResponse>> =
        performApiRequest { service.getExpoList() }

    override fun getExpoInformation(expoId: String): Flow<ExpoResponse> =
        performApiRequest { service.getExpoInformation(expoId = expoId) }

    override fun registerExpoInformation(body: ExpoResponse): Flow<ExpoIdResponse> =
        performApiRequest { service.registerExpoInformation(body = body) }

    override fun modifyExpoInformation(expoId: String, body: ExpoResponse): Flow<Unit> =
        performApiRequest { service.modifyExpoInformation(
            expoId = expoId,
            body = body
        ) }

    override fun deleteExpoInformation(expoId: String): Flow<Unit> =
        performApiRequest { service.deleteExpoInformation(expoId = expoId) }
}