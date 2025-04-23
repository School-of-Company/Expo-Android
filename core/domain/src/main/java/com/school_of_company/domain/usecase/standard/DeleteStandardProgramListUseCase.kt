package com.school_of_company.domain.usecase.standard

import com.school_of_company.data.repository.standard.StandardRepository
import javax.inject.Inject

class DeleteStandardProgramListUseCase @Inject constructor(
    private val repository: StandardRepository
) {
    operator fun invoke(standardProId: Long) = runCatching {
        repository.deleteStandardProgram(standardProId = standardProId)
    }
}