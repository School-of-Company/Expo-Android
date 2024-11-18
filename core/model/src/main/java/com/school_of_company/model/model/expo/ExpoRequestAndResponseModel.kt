package com.school_of_company.model.model.expo

data class ExpoRequestAndResponseModel(
    val title: String,
    val description: String,
    val startedDay: String, // yyyy-mm-dd
    val finishedDay: String, // yyyy-mm-dd
    val location: String,
    val coverImage: String?,
    val x: Float,
    val y: Float
)