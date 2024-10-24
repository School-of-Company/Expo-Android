package com.school_of_company.network.mapper.auth.request

import com.school_of_company.model.param.auth.AdminSignUpRequestParam
import com.school_of_company.network.dto.auth.request.AdminSignUpRequest

fun AdminSignUpRequestParam.toDto(): AdminSignUpRequest =
    AdminSignUpRequest(
        name = this.name,
        nickname = this.nickname,
        email = this.email,
        password = this.password,
        phoneNumber = this.phoneNumber
    )