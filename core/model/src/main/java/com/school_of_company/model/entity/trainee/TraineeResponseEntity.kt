package com.school_of_company.model.entity.trainee

data class TraineeResponseEntity(
    val id: Long,
    val name: String,
    val trainingId: String,
    val phoneNumber: String,
    val applicationType: String,
)
