package com.school_of_company.network.mapper.expo.request

import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel
import com.school_of_company.network.dto.expo.response.ExpoResponse

fun ExpoRequestAndResponseModel.toDto(): ExpoResponse =
    ExpoResponse(
        title = this.title,
        description = this.description,
        startedDay = this.startedDay,
        finishedDay = this.finishedDay,
        location = this.location,
        coverImage = this.coverImage,
        x = this.x,
        y = this.y
    )