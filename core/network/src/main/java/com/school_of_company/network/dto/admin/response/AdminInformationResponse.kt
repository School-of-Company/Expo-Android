package com.school_of_company.network.dto.admin.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdminInformationResponse(
    @Json(name = "name") val name: String,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "email") val email: String,
)
