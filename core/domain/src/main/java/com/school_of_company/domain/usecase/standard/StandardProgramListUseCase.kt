package com.school_of_company.domain.usecase.standard

import com.school_of_company.data.repository.standard.StandardRepository
import com.school_of_company.model.entity.standard.StandardProgramListResponseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StandardProgramListUseCase @Inject constructor(
    private val repository: StandardRepository
) {
    operator fun invoke(expoId: String) : Flow<List<StandardProgramListResponseEntity>> =
        repository.standardProgramList(expoId = expoId)
}