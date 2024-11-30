package com.school_of_company.domain.usecase.standard

import com.school_of_company.data.repository.standard.StandardRepository
import com.school_of_company.model.model.standard.StandardRequestModel
import javax.inject.Inject

class RegisterStandardProgramUseCase @Inject constructor(
    private val repository: StandardRepository
) {
    operator fun invoke(
        expoId: String,
        body: StandardRequestModel
    ) = runCatching {
        repository.registerStandardProgram(
            expoId = expoId,
            body = body
        )
    }
}