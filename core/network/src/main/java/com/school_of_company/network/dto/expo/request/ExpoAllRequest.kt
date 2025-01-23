package com.school_of_company.network.dto.expo.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExpoAllRequest(
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "startedDay") val startedDay: String, // yyyy-mm-dd
    @Json(name = "finishedDay") val finishedDay: String, // yyyy-mm-dd
    @Json(name = "location") val location: String,
    @Json(name = "coverImage") val coverImage: String?,
    @Json(name = "x") val x: Float,
    @Json(name = "y") val y: Float,
    @Json(name = "addStandardProRequestDto") val addStandardProRequestDto: List<StandardProRequestDto>,
    @Json(name = "addTrainingProRequestDto") val addTrainingProRequestDto: List<TrainingProRequestDto>
)

@JsonClass(generateAdapter = true)
data class StandardProRequestDto(
    @Json(name = "title") val title: String,
    @Json(name = "startedAt") val startedAt: String, // yyyy-MM-DD HH:mm
    @Json(name = "endedAt") val endedAt: String // yyyy-MM-DD HH:mm
)

@JsonClass(generateAdapter = true)
data class TrainingProRequestDto(
    @Json(name = "title") val title: String,
    @Json(name = "startedAt") val startedAt: String, // yyyy-MM-dd HH:mm
    @Json(name = "endedAt") val endedAt: String, // yyyy-MM-dd HH:mm
    @Json(name = "category") val category: String // ESSENTIAL, CHOICE
)
