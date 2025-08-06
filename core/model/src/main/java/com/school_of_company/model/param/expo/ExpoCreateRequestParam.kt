package com.school_of_company.model.param.expo

import androidx.compose.runtime.Immutable

data class ExpoAllRequestParam(
    val title: String,
    val description: String,
    val startedDay: String, // yyyy-mm-dd
    val finishedDay: String, // yyyy-mm-dd
    val location: String,
    val coverImage: String?,
    val x: String,
    val y: String,
    val addStandardProRequestDto: List<StandardProRequestParam>,
    val addTrainingProRequestDto: List<TrainingProRequestParam>,
)

@Immutable
data class StandardProRequestParam(
    val title: String,
    val startedAt: String, // yyyy-MM-DD HH:mm
    val endedAt: String, // yyyy-MM-DD HH:mm
)

@Immutable
data class TrainingProRequestParam(
    val title: String,
    val startedAt: String, // yyyy-MM-dd HH:mm
    val endedAt: String, // yyyy-MM-dd HH:mm
    val category: String, // ESSENTIAL, CHOICE
)