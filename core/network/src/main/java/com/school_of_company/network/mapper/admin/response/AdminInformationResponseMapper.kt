package com.school_of_company.network.mapper.admin.response

import com.school_of_company.model.entity.admin.AdminInformationResponseEntity
import com.school_of_company.network.dto.admin.response.AdminInformationResponse

fun AdminInformationResponse.toEntity(): AdminInformationResponseEntity =
    AdminInformationResponseEntity(
        name = this.name,
        nickname = this.nickname,
        email = this.email
    )