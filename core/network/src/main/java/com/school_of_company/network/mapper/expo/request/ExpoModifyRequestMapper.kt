package com.school_of_company.network.mapper.expo.request

import com.school_of_company.model.param.expo.ExpoModifyRequestParam
import com.school_of_company.model.param.expo.StandardProIdRequestParam
import com.school_of_company.model.param.expo.TrainingProIdRequestParam
import com.school_of_company.network.dto.expo.request.ExpoModifyRequest
import com.school_of_company.network.dto.expo.request.StandardProIdRequestDto
import com.school_of_company.network.dto.expo.request.TrainingProIdRequestDto

fun ExpoModifyRequestParam.toParam(): ExpoModifyRequest =
    ExpoModifyRequest(
        title = this.title,
        description = this.description,
        startedDay = this.startedDay,
        finishedDay = this.finishedDay,
        location = this.location,
        coverImage = this.coverImage,
        x = this.x,
        y = this.y,
        updateStandardProRequestDto = updateStandardProRequestDto.map { it.toParam() },
        updateTrainingProRequestDto = this.updateTrainingProRequestDto.map { it.toParam() }
    )

fun StandardProIdRequestParam.toParam(): StandardProIdRequestDto =
    StandardProIdRequestDto(
        id = id,
        title = this.title,
        startedAt = this.startedAt,
        endedAt = this.endedAt
    )

fun TrainingProIdRequestParam.toParam(): TrainingProIdRequestDto =
    TrainingProIdRequestDto(
        id = id,
        title = this.title,
        startedAt = this.startedAt,
        endedAt = this.endedAt,
        category = this.category
    )
