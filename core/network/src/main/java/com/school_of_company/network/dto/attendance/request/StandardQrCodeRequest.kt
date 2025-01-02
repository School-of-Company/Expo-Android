package com.school_of_company.network.dto.attendance.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StandardQrCodeRequest(
    @Json(name = "participantId") val participantId: Long,
    @Json(name = "phoneNumber") val phoneNumber: String,
)
