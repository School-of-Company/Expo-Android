package com.school_of_company.network.dto.training.all

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrainingDto(
    @Json(name = "title") val title: String,
    @Json(name = "startedAt") val startedAt: String,
    @Json(name = "endedAt") val endedAt: String,
    @Json(name = "category") val category: String,
)
