package com.school_of_company.network.dto.participant.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParticipantInformationResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "phoneNumber") val phoneNumber: String,
    @Json(name = "informationStatus") val informationStatus: Boolean,
)
