package com.school_of_company.domain.usecase.attendance

import com.school_of_company.data.repository.attendance.AttendanceRepository
import com.school_of_company.model.param.attendance.StandardQrCodeRequestParam
import javax.inject.Inject

class StandardQrCodeRequestUseCase @Inject constructor(
    private val repository: AttendanceRepository
) {
    operator fun invoke(
        standardId: Long,
        body: StandardQrCodeRequestParam
    ) = runCatching {
        repository.standardQrCode(
            standardId = standardId,
            body = body
        )
    }
}