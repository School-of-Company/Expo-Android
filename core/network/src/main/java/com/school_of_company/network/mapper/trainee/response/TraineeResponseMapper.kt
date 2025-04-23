package com.school_of_company.network.mapper.trainee.response

import com.school_of_company.model.entity.trainee.TraineeResponseEntity
import com.school_of_company.network.dto.trainee.response.TraineeResponse

fun TraineeResponse.toEntity(): TraineeResponseEntity =
    TraineeResponseEntity(
        id = this.id,
        name = this.name,
        trainingId = this.trainingId,
        phoneNumber = this.phoneNumber,
        applicationType = this.applicationType
    )