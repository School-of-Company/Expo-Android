package com.school_of_company.network.mapper.expo.request

import com.school_of_company.model.param.expo.ExpoAllRequestParam
import com.school_of_company.model.param.expo.StandardProRequestParam
import com.school_of_company.model.param.expo.TrainingProRequestParam
import com.school_of_company.network.dto.expo.request.ExpoAllRequest
import com.school_of_company.network.dto.expo.request.StandardProRequestDto
import com.school_of_company.network.dto.expo.request.TrainingProRequestDto

fun ExpoAllRequestParam.toParam(): ExpoAllRequest =
    ExpoAllRequest(
        title = this.title,
        description = this.description,
        startedDay = this.startedDay,
        finishedDay = this.finishedDay,
        location = this.location,
        coverImage = this.coverImage,
        x = this.x,
        y = this.y,
        addStandardProRequestDto = this.addStandardProRequestDto.map { it.toParam() },
        addTrainingProRequestDto = this.addTrainingProRequestDto.map { it.toParam() }
    )

fun StandardProRequestParam.toParam(): StandardProRequestDto =
    StandardProRequestDto(
        title = this.title,
        startedAt = this.startedAt,
        endedAt = this.endedAt
    )

fun TrainingProRequestParam.toParam(): TrainingProRequestDto =
    TrainingProRequestDto(
        title = this.title,
        startedAt = this.startedAt,
        endedAt = this.endedAt,
        category = this.category
    )
