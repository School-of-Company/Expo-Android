package com.school_of_company.network.dto.standard.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StandardProgramListResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "startedAt") val startedAt: String,
    @Json(name = "endedAt") val endedAt: String,
)
