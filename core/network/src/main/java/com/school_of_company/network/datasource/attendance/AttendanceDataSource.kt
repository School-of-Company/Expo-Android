package com.school_of_company.network.datasource.attendance

import com.school_of_company.network.dto.attendance.request.StandardQrCodeRequest
import com.school_of_company.network.dto.attendance.request.TrainingQrCodeRequest
import kotlinx.coroutines.flow.Flow

interface AttendanceDataSource {
    fun trainingQrCode(trainingId: Long, body: TrainingQrCodeRequest) : Flow<Unit>
    fun standardQrCode(standardId: Long, body: StandardQrCodeRequest) : Flow<Unit>

}