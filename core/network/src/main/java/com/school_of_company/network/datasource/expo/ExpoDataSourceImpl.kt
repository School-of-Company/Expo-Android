package com.school_of_company.network.datasource.expo

import com.school_of_company.network.api.ExpoAPI
import com.school_of_company.network.dto.expo.request_response.ExpoRequestAndResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpoDataSourceImpl @Inject constructor(
    private val service: ExpoAPI
) : ExpoDataSource {
    override fun getExpoInformation(expoId: Long): Flow<ExpoRequestAndResponse> =
        performApiRequest { service.getExpoInformation(expoId = expoId) }

    override fun registerExpoInformation(body: ExpoRequestAndResponse): Flow<Unit> =
        performApiRequest { service.registerExpoInformation(body = body) }

    override fun modifyExpoInformation(expoId: Long, body: ExpoRequestAndResponse): Flow<Unit> =
        performApiRequest { service.modifyExpoInformation(
            expoId = expoId,
            body = body
        ) }

    override fun deleteExpoInformation(expoId: Long): Flow<Unit> =
        performApiRequest { service.deleteExpoInformation(expoId = expoId) }
}