package com.school_of_company.data.repository.standard

import com.school_of_company.model.entity.standard.StandardAttendListResponseEntity
import com.school_of_company.model.entity.standard.StandardProgramListResponseEntity
import com.school_of_company.model.model.standard.StandardRequestModel
import kotlinx.coroutines.flow.Flow

interface StandardRepository {
    fun registerStandardProgram(expoId: String, body: StandardRequestModel): Flow<Unit>
    fun registerStandardListProgram(expoId: String, body: List<StandardRequestModel>): Flow<Unit>
    fun modifyStandardProgram(standardProId: Long, body: StandardRequestModel): Flow<Unit>
    fun deleteStandardProgram(standardProId: Long): Flow<Unit>
    fun standardProgramList(expoId: String): Flow<List<StandardProgramListResponseEntity>>
    fun standardProgramAttendList(standardProId: Long): Flow<List<StandardAttendListResponseEntity>>
}