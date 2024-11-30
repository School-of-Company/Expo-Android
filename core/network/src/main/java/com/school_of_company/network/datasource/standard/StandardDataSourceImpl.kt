package com.school_of_company.network.datasource.standard

import com.school_of_company.network.api.StandardAPI
import com.school_of_company.network.dto.standard.request.StandardRequest
import com.school_of_company.network.dto.standard.response.StandardAttendListResponse
import com.school_of_company.network.dto.standard.response.StandardProgramListResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StandardDataSourceImpl @Inject constructor(
    private val service: StandardAPI
) : StandardDataSource {
    override fun registerStandardProgram(expoId: String, body: StandardRequest): Flow<Unit> =
        performApiRequest { service.registerStandardProgram(
            expoId = expoId,
            body = body
        ) }

    override fun registerStandardListProgram(
        expoId: String,
        body: List<StandardRequest>
    ): Flow<Unit> =
        performApiRequest { service.registerStandardListProgram(
            expoId = expoId,
            body = body
        ) }

    override fun modifyStandardProgram(standardProId: Long, body: StandardRequest): Flow<Unit> =
        performApiRequest { service.modifyStandardProgram(
            standardProId = standardProId,
            body = body
        ) }

    override fun deleteStandardProgram(standardProId: Long): Flow<Unit> =
        performApiRequest { service.deleteStandardProgram(standardProId = standardProId) }

    override fun standardProgramList(expoId: String): Flow<List<StandardProgramListResponse>> =
        performApiRequest { service.standardProgramList(expoId = expoId) }

    override fun standardProgramAttendList(standardProId: Long): Flow<List<StandardAttendListResponse>> =
        performApiRequest { service.standardProgramAttendList(standardProId = standardProId) }
}