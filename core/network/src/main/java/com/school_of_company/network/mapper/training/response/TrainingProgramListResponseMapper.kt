package com.school_of_company.network.mapper.training.response

import com.school_of_company.model.entity.training.TrainingProgramListResponseEntity
import com.school_of_company.network.dto.training.response.TrainingProgramListResponse
import com.squareup.moshi.Json

fun TrainingProgramListResponse.toEntity(): TrainingProgramListResponseEntity =
    TrainingProgramListResponseEntity(
        essential = this.essential.map { it.toEntity() },
        choice = this.choice.map { it.toEntity() }
    )

fun TrainingProgramListResponse.Training.toEntity(): TrainingProgramListResponseEntity.Training =
    TrainingProgramListResponseEntity.Training(
        title = this.title,
        startedAt = this.startedAt,
        endedAt = this.endedAt
    )