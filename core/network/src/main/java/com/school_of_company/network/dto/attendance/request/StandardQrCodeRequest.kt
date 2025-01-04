package com.school_of_company.network.dto.attendance.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StandardQrCodeRequest(
    val participantId: Long,
    val phoneNumber: String,
)