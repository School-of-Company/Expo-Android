package com.school_of_company.network.dto.standard.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StandardRequest(
    @Json(name = "title") val title: String,
    @Json(name = "startedAt") val startedAt: String,
    @Json(name = "endedAt") val endedAt: String,
)
