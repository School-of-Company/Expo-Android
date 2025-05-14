package com.school_of_company.model.entity.standard

import androidx.compose.runtime.Immutable

@Immutable
data class StandardProgramListResponseEntity(
    val id: Long,
    val title: String,
    val startedAt: String,
    val endedAt: String,
)
