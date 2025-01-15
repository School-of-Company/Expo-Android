package com.school_of_company.network.dto.sms.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendSmsToParticipantTraineeResponse(
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "content") val authority: String,
)