package com.school_of_company.network.mapper.training.response

import com.school_of_company.model.model.training.TrainingDtoModel
import com.school_of_company.network.dto.training.all.TrainingDto

fun TrainingDto.toModel(): TrainingDtoModel =
    TrainingDtoModel(
        title = this.title,
        startedAt = this.startedAt,
        finishedAt = this.finishedAt,
        category = this.category
    )