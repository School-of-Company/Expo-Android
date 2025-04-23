package com.school_of_company.network.dto.auth.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdminSignUpRequest(
    @Json(name = "name") val name: String,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
    @Json(name = "phoneNumber") val phoneNumber: String,
)
