package com.school_of_company.network.mapper.expo.response

import com.school_of_company.model.entity.expo.ExpoIdResponseEntity
import com.school_of_company.network.dto.expo.response.ExpoIdResponse

fun ExpoIdResponse.toEntity(): ExpoIdResponseEntity =
    ExpoIdResponseEntity(expoId = this.expoId)