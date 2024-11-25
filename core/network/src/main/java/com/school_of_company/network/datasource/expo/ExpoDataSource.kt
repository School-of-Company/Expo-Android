package com.school_of_company.network.datasource.expo

import com.school_of_company.network.dto.expo.request_response.ExpoRequestAndResponse
import com.school_of_company.network.dto.expo.response.ExpoListResponse
import kotlinx.coroutines.flow.Flow

interface ExpoDataSource {
    fun getExpoList() : Flow<List<ExpoListResponse>>
    fun getExpoInformation(expoId: String) : Flow<ExpoRequestAndResponse>
    fun registerExpoInformation(body: ExpoRequestAndResponse) : Flow<Unit>
    fun modifyExpoInformation(expoId: String, body: ExpoRequestAndResponse) : Flow<Unit>
    fun deleteExpoInformation(expoId: String) : Flow<Unit>
}