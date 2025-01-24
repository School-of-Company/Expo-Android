package com.school_of_company.network.datasource.expo

import com.school_of_company.network.dto.expo.request.ExpoAllRequest
import com.school_of_company.network.dto.expo.request.ExpoModifyRequest
import com.school_of_company.network.dto.expo.response.ExpoResponse
import com.school_of_company.network.dto.expo.response.ExpoIdResponse
import com.school_of_company.network.dto.expo.response.ExpoListResponse
import kotlinx.coroutines.flow.Flow

interface ExpoDataSource {
    fun getExpoList() : Flow<List<ExpoListResponse>>
    fun getExpoInformation(expoId: String) : Flow<ExpoResponse>
    fun registerExpoInformation(body: ExpoAllRequest) : Flow<ExpoIdResponse>
    fun modifyExpoInformation(expoId: String, body: ExpoModifyRequest) : Flow<Unit>
    fun deleteExpoInformation(expoId: String) : Flow<Unit>
}