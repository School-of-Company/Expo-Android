package com.school_of_company.domain.usecase.attendance

import com.school_of_company.data.repository.attendance.AttendanceRepository
import com.school_of_company.model.param.attendance.TrainingQrCodeRequestParam
import javax.inject.Inject

class TrainingQrCodeRequestUseCase @Inject constructor(
    private val repository: AttendanceRepository
) {
    operator fun invoke(
        trainingId: Long,
        body: TrainingQrCodeRequestParam
    ) = runCatching {
        repository.trainingQrCode(
            trainingId = trainingId,
            body = body
        )
    }
}