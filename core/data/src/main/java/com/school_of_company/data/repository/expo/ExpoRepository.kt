package com.school_of_company.data.repository.expo

import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel
import kotlinx.coroutines.flow.Flow

interface ExpoRepository {
    fun getExpoInformation(expoId: Long) : Flow<ExpoRequestAndResponseModel>
    fun registerExpoInformation(body: ExpoRequestAndResponseModel) : Flow<Unit>
    fun modifyExpoInformation(expoId: Long, body: ExpoRequestAndResponseModel) : Flow<Unit>
    fun deleteExpoInformation(expoId: Long) : Flow<Unit>
}