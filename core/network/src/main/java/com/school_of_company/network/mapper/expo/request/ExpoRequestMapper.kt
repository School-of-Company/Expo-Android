package com.school_of_company.network.mapper.expo.request

import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel
import com.school_of_company.network.dto.expo.all.ExpoRequestAndResponse

fun ExpoRequestAndResponseModel.toDto(): ExpoRequestAndResponse =
    ExpoRequestAndResponse(
        title = this.title,
        description = this.description,
        startedDay = this.startedDay,
        finishedDay = this.finishedDay,
        location = this.location,
        coverImage = this.coverImage,
        x = this.x,
        y = this.y
    )