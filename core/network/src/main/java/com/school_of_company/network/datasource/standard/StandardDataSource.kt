package com.school_of_company.network.datasource.standard

import com.school_of_company.network.dto.standard.response.StandardAttendListResponse
import com.school_of_company.network.dto.standard.response.StandardProgramListResponse
import kotlinx.coroutines.flow.Flow

interface StandardDataSource {
    fun deleteStandardProgram(standardProId: Long) : Flow<Unit>
    fun standardProgramList(expoId: String) : Flow<List<StandardProgramListResponse>>
    fun standardProgramAttendList(standardProId: Long) : Flow<List<StandardAttendListResponse>>
}