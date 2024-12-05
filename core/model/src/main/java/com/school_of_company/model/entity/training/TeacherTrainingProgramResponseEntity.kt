package com.school_of_company.model.entity.training

data class TeacherTrainingProgramResponseEntity(
    val id: Long,
    val name: String,
    val organization: String,
    val position: String,
    val programName: String,
    val status: Boolean,
    val entryTime: String?,
    val leaveTime: String?
)