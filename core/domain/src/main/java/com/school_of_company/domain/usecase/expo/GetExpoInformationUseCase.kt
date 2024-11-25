package com.school_of_company.domain.usecase.expo

import com.school_of_company.data.repository.expo.ExpoRepository
import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpoInformationUseCase @Inject constructor(
    private val repository: ExpoRepository
) {
    operator fun invoke(expoId: String) : Flow<ExpoRequestAndResponseModel> =
        repository.getExpoInformation(expoId = expoId)
}