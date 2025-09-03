package com.school_of_company.network.dto.expo.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExpoResponse(
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "startedDay") val startedDay: String, // yyyy-mm-dd
    @Json(name = "finishedDay") val finishedDay: String, // yyyy-mm-dd
    @Json(name = "location") val location: String,
    @Json(name = "coverImage") val coverImage: String?,
    @Json(name = "x") val x: String,
    @Json(name = "y") val y: String
)
