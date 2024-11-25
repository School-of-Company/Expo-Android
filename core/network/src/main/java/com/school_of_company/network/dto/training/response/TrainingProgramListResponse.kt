package com.school_of_company.network.dto.training.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrainingProgramListResponse(
    @Json(name = "essential") val essential: List<Training>,
    @Json(name = "choice") val choice: List<Training>
) {
    data class Training(
        @Json(name = "title") val title: String,
        @Json(name = "startedAt") val startedAt: String,
        @Json(name = "endedAt") val endedAt: String
    )
}