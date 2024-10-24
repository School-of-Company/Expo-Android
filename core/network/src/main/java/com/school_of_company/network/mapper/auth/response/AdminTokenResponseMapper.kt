package com.school_of_company.network.mapper.auth.response

import com.school_of_company.model.model.auth.AdminTokenResponseModel
import com.school_of_company.network.dto.auth.response.AdminTokenResponse

fun AdminTokenResponse.toModel(): AdminTokenResponseModel =
    AdminTokenResponseModel(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        accessTokenExpiresIn = this.accessTokenExpiresIn,
        refreshTokenExpiresIn = this.refreshTokenExpiresIn
    )