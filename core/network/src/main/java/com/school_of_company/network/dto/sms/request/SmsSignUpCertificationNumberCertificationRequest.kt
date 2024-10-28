package com.school_of_company.network.dto.sms.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SmsSignUpCertificationNumberCertificationRequest(
    @Json(name = "phoneNumber") val phoneNumber: String,
    @Json(name = "randomCode") val randomCode: String,
)