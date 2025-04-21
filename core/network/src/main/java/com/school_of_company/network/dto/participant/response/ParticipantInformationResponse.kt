package com.school_of_company.network.dto.participant.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParticipantInformationResponse(
    @Json(name = "info") val info: PageInfo,
    @Json(name = "participants") val participants: List<ParticipantResponse>
)

@JsonClass(generateAdapter = true)
data class PageInfo(
    @Json(name = "totalPage") val totalPage: Int,
    @Json(name = "totalElement") val totalElement: Int
)

@JsonClass(generateAdapter = true)
data class ParticipantResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "phoneNumber") val phoneNumber: String,
    @Json(name = "informationStatus") val informationStatus: Boolean
)
