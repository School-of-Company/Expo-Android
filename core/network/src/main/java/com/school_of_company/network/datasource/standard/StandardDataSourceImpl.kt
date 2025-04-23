package com.school_of_company.network.datasource.standard

import com.school_of_company.network.api.StandardAPI
import com.school_of_company.network.dto.standard.response.StandardAttendListResponse
import com.school_of_company.network.dto.standard.response.StandardProgramListResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StandardDataSourceImpl @Inject constructor(
    private val service: StandardAPI
) : StandardDataSource {
    override fun deleteStandardProgram(standardProId: Long): Flow<Unit> =
        performApiRequest { service.deleteStandardProgram(standardProId = standardProId) }

    override fun standardProgramList(expoId: String): Flow<List<StandardProgramListResponse>> =
        performApiRequest { service.standardProgramList(expoId = expoId) }

    override fun standardProgramAttendList(standardProId: Long): Flow<List<StandardAttendListResponse>> =
        performApiRequest { service.standardProgramAttendList(standardProId = standardProId) }
}