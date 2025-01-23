package com.school_of_company.data.repository.expo

import com.school_of_company.model.entity.expo.ExpoIdResponseEntity
import com.school_of_company.model.entity.expo.ExpoListResponseEntity
import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel
import com.school_of_company.model.param.expo.ExpoAllRequestParam
import kotlinx.coroutines.flow.Flow

interface ExpoRepository {
    fun getExpoList() : Flow<List<ExpoListResponseEntity>>
    fun getExpoInformation(expoId: String) : Flow<ExpoRequestAndResponseModel>
    fun registerExpoInformation(body: ExpoAllRequestParam) : Flow<ExpoIdResponseEntity>
    fun modifyExpoInformation(expoId: String, body: ExpoAllRequestParam) : Flow<Unit>
    fun deleteExpoInformation(expoId: String) : Flow<Unit>
}