package com.school_of_company.data.repository.expo

import com.school_of_company.model.entity.expo.ExpoListResponseEntity
import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel
import kotlinx.coroutines.flow.Flow

interface ExpoRepository {
    fun getExpoList() : Flow<List<ExpoListResponseEntity>>
    fun getExpoInformation(expoId: String) : Flow<ExpoRequestAndResponseModel>
    fun registerExpoInformation(body: ExpoRequestAndResponseModel) : Flow<Unit>
    fun modifyExpoInformation(expoId: String, body: ExpoRequestAndResponseModel) : Flow<Unit>
    fun deleteExpoInformation(expoId: String) : Flow<Unit>
}