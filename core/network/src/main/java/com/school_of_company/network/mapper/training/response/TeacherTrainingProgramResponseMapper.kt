package com.school_of_company.network.mapper.training.response

import com.school_of_company.model.entity.training.TeacherTrainingProgramResponseEntity
import com.school_of_company.network.dto.training.response.TeacherTrainingProgramResponse

fun TeacherTrainingProgramResponse.toEntity(): TeacherTrainingProgramResponseEntity =
    TeacherTrainingProgramResponseEntity(
        id = this.id,
        name = this.name,
        programName = this.programName,
        status = this.status,
        entryTime = this.entryTime,
        leaveTime = this.leaveTime
    )