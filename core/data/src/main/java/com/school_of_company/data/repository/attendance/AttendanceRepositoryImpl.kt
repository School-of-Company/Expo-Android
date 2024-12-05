package com.school_of_company.data.repository.attendance

import com.school_of_company.model.param.attendance.TrainingQrCodeRequestParam
import com.school_of_company.network.datasource.attendance.AttendanceDataSource
import com.school_of_company.network.mapper.attendance.request.toDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val dataSource: AttendanceDataSource
) : AttendanceRepository {
    override fun trainingQrCode(
        trainingId: Long,
        body: TrainingQrCodeRequestParam
    ): Flow<Unit> {
        return dataSource.trainingQrCode(
            trainingId = trainingId,
            body = body.toDto()
        )
    }
}