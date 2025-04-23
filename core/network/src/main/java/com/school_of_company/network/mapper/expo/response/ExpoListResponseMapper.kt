package com.school_of_company.network.mapper.expo.response

import com.school_of_company.model.entity.expo.ExpoListResponseEntity
import com.school_of_company.network.dto.expo.response.ExpoListResponse

fun ExpoListResponse.toEntity(): ExpoListResponseEntity =
    ExpoListResponseEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        startedDay = this.startedDay,
        finishedDay = this.finishedDay,
        coverImage = this.coverImage
    )