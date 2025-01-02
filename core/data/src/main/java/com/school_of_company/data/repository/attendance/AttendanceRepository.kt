package com.school_of_company.data.repository.attendance

import com.school_of_company.model.param.attendance.StandardQrCodeRequestParam
import com.school_of_company.model.param.attendance.TrainingQrCodeRequestParam
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    fun trainingQrCode(trainingId: Long, body: TrainingQrCodeRequestParam) : Flow<Unit>
    fun standardQrCode(standardId: Long, body: StandardQrCodeRequestParam) : Flow<Unit>
}