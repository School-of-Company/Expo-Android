package com.school_of_company.network.dto.expo.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExpoListResponse(
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "startedDay") val startedDay: String, // yyyy-mm-dd
    @Json(name = "finishedDay") val finishedDay: String, // yyyy-mm-dd
    @Json(name = "coverImage") val coverImage: String?,
)
