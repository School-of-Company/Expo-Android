package com.school_of_company.network.datasource.standard

import com.school_of_company.network.dto.standard.request.StandardRequest
import com.school_of_company.network.dto.standard.response.StandardAttendListResponse
import com.school_of_company.network.dto.standard.response.StandardProgramListResponse
import kotlinx.coroutines.flow.Flow

interface StandardDataSource {
    fun registerStandardProgram(expoId: String, body: StandardRequest) : Flow<Unit>
    fun registerStandardListProgram(expoId: String, body: List<StandardRequest>) : Flow<Unit>
    fun modifyStandardProgram(standardProId: Long, body: StandardRequest) : Flow<Unit>
    fun deleteStandardProgram(standardProId: Long) : Flow<Unit>
    fun standardProgramList(expoId: String) : Flow<List<StandardProgramListResponse>>
    fun standardProgramAttendList(standardProId: Long) : Flow<List<StandardAttendListResponse>>
}