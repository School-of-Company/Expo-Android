package com.school_of_company.network.mapper.admin.response

import com.school_of_company.model.entity.admin.AdminRequestAllowListResponseEntity
import com.school_of_company.network.dto.admin.response.AdminRequestAllowListResponse

fun AdminRequestAllowListResponse.toEntity(): AdminRequestAllowListResponseEntity =
    AdminRequestAllowListResponseEntity(
        id = this.id,
        name = this.name,
        nickname = this.nickname,
        email = this.email,
        phoneNumber = this.phoneNumber
    )