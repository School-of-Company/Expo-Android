package com.school_of_company.network.mapper.auth.request

import com.school_of_company.model.param.auth.AdminSignInRequestParam
import com.school_of_company.network.dto.auth.request.AdminSignInRequest

fun AdminSignInRequestParam.toDto(): AdminSignInRequest =
    AdminSignInRequest(
        nickname = this.nickname,
        password = this.password
    )