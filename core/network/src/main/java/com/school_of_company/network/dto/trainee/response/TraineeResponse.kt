package com.school_of_company.network.dto.trainee.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TraineeResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "trainingId") val trainingId: String,
    @Json(name = "phoneNumber") val phoneNumber: String,
    @Json(name = "applicationType") val applicationType: String,
)
