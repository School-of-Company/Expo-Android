package com.school_of_company.domain.usecase.expo

import com.school_of_company.data.repository.expo.ExpoRepository
import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel
import com.school_of_company.model.param.expo.ExpoAllRequestParam
import com.school_of_company.model.param.expo.ExpoModifyRequestParam
import javax.inject.Inject

class ModifyExpoInformationUseCase @Inject constructor(
    private val repository: ExpoRepository
) {
    operator fun invoke(
        expoId: String,
        body: ExpoModifyRequestParam
    ) = runCatching {
        repository.modifyExpoInformation(
            expoId = expoId,
            body = body
        )
    }
}