package com.school_of_company.model.entity.expo

data class ExpoListResponseEntity(
    val id: Long,
    val title: String,
    val description: String,
    val startedDay: String, // yyyy-mm-dd
    val finishedDay: String, // yyyy-mm-dd
    val coverImage: String?,
)
