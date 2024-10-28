package com.school_of_company.network.dto.sms.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SmsSignUpCertificationNumberSendRequest(
    @Json(name = "phoneNumber") val phoneNumber: String,
)
