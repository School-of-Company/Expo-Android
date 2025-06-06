package com.school_of_company.model.entity.expo

import androidx.compose.runtime.Immutable

@Immutable
data class ExpoListResponseEntity(
    val id: String,
    val title: String,
    val description: String,
    val startedDay: String, // yyyy-mm-dd
    val finishedDay: String, // yyyy-mm-dd
    val coverImage: String?,
)
