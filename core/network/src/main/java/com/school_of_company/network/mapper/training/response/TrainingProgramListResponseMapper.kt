package com.school_of_company.network.mapper.training.response

import com.school_of_company.model.entity.training.TrainingProgramListResponseEntity
import com.school_of_company.network.dto.training.response.TrainingProgramListResponse
import com.squareup.moshi.Json

fun TrainingProgramListResponse.toEntity(): TrainingProgramListResponseEntity =
    TrainingProgramListResponseEntity(
        id = this.id,
        title = this.title,
        startedAt = this.startedAt,
        endedAt = this.endedAt,
        category = this.category
    )