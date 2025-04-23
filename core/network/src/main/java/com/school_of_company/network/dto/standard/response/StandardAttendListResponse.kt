package com.school_of_company.network.dto.standard.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StandardAttendListResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "programName") val programName: String,
    @Json(name = "status") val status: Boolean,
    @Json(name = "entryTime") val entryTime: String?,
    @Json(name = "leaveTime") val leaveTime: String?,
)
