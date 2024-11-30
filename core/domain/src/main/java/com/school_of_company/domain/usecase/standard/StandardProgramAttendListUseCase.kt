package com.school_of_company.domain.usecase.standard

import com.school_of_company.data.repository.standard.StandardRepository
import com.school_of_company.model.entity.standard.StandardAttendListResponseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StandardProgramAttendListUseCase @Inject constructor(
    private val repository: StandardRepository
) {
    operator fun invoke(standardProId: Long) : Flow<List<StandardAttendListResponseEntity>> =
        repository.standardProgramAttendList(standardProId = standardProId)
}