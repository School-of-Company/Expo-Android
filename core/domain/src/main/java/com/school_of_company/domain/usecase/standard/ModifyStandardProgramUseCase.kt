package com.school_of_company.domain.usecase.standard

import com.school_of_company.data.repository.standard.StandardRepository
import com.school_of_company.model.model.standard.StandardRequestModel
import javax.inject.Inject

class ModifyStandardProgramUseCase @Inject constructor(
    private val repository: StandardRepository
) {
    operator fun invoke(
        standardProId: Long,
        body: StandardRequestModel
    ) = runCatching {
        repository.modifyStandardProgram(
            standardProId = standardProId,
            body = body
        )

    }
}