package com.school_of_company.data.repository.standard

import com.school_of_company.model.entity.standard.StandardAttendListResponseEntity
import com.school_of_company.model.entity.standard.StandardProgramListResponseEntity
import kotlinx.coroutines.flow.Flow

interface StandardRepository {
    fun deleteStandardProgram(standardProId: Long): Flow<Unit>
    fun standardProgramList(expoId: String): Flow<List<StandardProgramListResponseEntity>>
    fun standardProgramAttendList(standardProId: Long): Flow<List<StandardAttendListResponseEntity>>
}