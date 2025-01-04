package com.school_of_company.network.api

import com.school_of_company.network.dto.attendance.request.StandardQrCodeRequest
import com.school_of_company.network.dto.attendance.request.TrainingQrCodeRequest
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AttendanceAPI {

    @PATCH("/attendance/training/{trainingPro_id}")
    suspend fun trainingQrCode(
        @Path("trainingPro_id") trainingId: Long,
        @Body body: TrainingQrCodeRequest
    )
    @PATCH("/attendance/training/{standardPro_id}")
    suspend fun standardQrCode(
        @Path("standardPro_id") standardId: Long,
        @Body body: StandardQrCodeRequest
    )
}