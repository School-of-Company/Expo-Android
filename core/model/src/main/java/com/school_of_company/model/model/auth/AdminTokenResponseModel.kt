package com.school_of_company.model.model.auth

data class AdminTokenResponseModel(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: String,
    val refreshTokenExpiresIn: String
)
