package com.school_of_company.domain.usecase.expo

import com.school_of_company.data.repository.expo.ExpoRepository
import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel
import javax.inject.Inject

class ModifyExpoInformationUseCase @Inject constructor(
    private val repository: ExpoRepository
) {
    operator fun invoke(
        expoId: Long,
        body: ExpoRequestAndResponseModel
    ) = runCatching {
        repository.modifyExpoInformation(
            expoId = expoId,
            body = body
        )
    }
}