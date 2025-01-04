package com.school_of_company.network.datasource.attendance

import com.school_of_company.network.api.AttendanceAPI
import com.school_of_company.network.dto.attendance.request.StandardQrCodeRequest
import com.school_of_company.network.dto.attendance.request.TrainingQrCodeRequest
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AttendanceDataSourceImpl @Inject constructor(
    private val service: AttendanceAPI
) : AttendanceDataSource {
    override fun trainingQrCode(
        trainingId: Long,
        body: TrainingQrCodeRequest
    ): Flow<Unit> =
        performApiRequest { service.trainingQrCode(
            body = body,
            trainingId = trainingId
        ) }

    override fun standardQrCode(
        standardId: Long,
        body: StandardQrCodeRequest
    ): Flow<Unit> =
        performApiRequest { service.standardQrCode(
            body = body,
            standardId = standardId
        ) }
}