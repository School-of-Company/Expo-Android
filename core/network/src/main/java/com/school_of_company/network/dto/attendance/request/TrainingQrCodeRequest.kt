package com.school_of_company.network.dto.attendance.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrainingQrCodeRequest(
    @Json(name = "traineeId") val traineeId: Long
)