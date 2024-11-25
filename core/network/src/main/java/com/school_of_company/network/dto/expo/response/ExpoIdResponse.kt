package com.school_of_company.network.dto.expo.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExpoIdResponse(
    @Json(name = "expoId") val expoId: String,
)
