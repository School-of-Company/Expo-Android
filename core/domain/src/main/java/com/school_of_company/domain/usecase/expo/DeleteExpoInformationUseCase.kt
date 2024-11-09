package com.school_of_company.domain.usecase.expo

import com.school_of_company.data.repository.expo.ExpoRepository
import javax.inject.Inject

class DeleteExpoInformationUseCase @Inject constructor(
    private val repository: ExpoRepository
) {
    operator fun invoke(expoId: Long) = runCatching {
        repository.deleteExpoInformation(expoId = expoId)
    }
}