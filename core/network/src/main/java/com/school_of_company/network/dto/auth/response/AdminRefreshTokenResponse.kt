package com.school_of_company.network.dto.auth.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdminRefreshTokenResponse(
    @Json(name = "accessToken") val accessToken: String,
    @Json(name = "refreshToken") val refreshToken: String,
    @Json(name = "accessTokenExpiresIn") val accessTokenExpiresIn: String,
    @Json(name = "refreshTokenExpiresIn") val refreshTokenExpiresIn: String
)
