package com.school_of_company.data.repository.standard

import com.school_of_company.model.entity.standard.StandardAttendListResponseEntity
import com.school_of_company.model.entity.standard.StandardProgramListResponseEntity
import com.school_of_company.network.datasource.standard.StandardDataSource
import com.school_of_company.network.mapper.standard.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class StandardRepositoryImpl @Inject constructor(
    private val dataSource: StandardDataSource
) : StandardRepository {
    override fun deleteStandardProgram(standardProId: Long): Flow<Unit> {
        return dataSource.deleteStandardProgram(standardProId = standardProId)
    }

    override fun standardProgramList(expoId: String): Flow<List<StandardProgramListResponseEntity>> {
        return dataSource.standardProgramList(expoId = expoId).transform { list -> emit(list.map { it.toModel() }) }
    }

    override fun standardProgramAttendList(standardProId: Long): Flow<List<StandardAttendListResponseEntity>> {
        return dataSource.standardProgramAttendList(standardProId = standardProId).transform { list -> emit(list.map { it.toModel() }) }
    }
}