package com.school_of_company.network.dto.training.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeacherTrainingProgramResponse(
    @Json(name = "name") val name: String,
    @Json(name = "organization") val organization: String,
    @Json(name = "position") val position: String,
    @Json(name = "programName") val programName: String,
    @Json(name = "status") val status: Boolean,
    @Json(name = "entryTime") val entryTime: String,
    @Json(name = "leaveTime") val leaveTime: String
)
