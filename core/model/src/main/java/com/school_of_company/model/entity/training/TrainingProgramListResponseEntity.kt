package com.school_of_company.model.entity.training

import androidx.compose.runtime.Immutable

@Immutable
data class TrainingProgramListResponseEntity(
    val id: Long,
    val title: String,
    val startedAt: String,
    val endedAt: String,
    val category: String
)
