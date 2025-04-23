package com.school_of_company.model.entity.training

data class TrainingProgramListResponseEntity(
    val id: Long,
    val title: String,
    val startedAt: String,
    val endedAt: String,
    val category: String
)
