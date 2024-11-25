package com.school_of_company.model.entity.training

data class TrainingProgramListResponseEntity(
    val essential: List<Training>,
    val choice: List<Training>
) {
    data class Training(
        val title: String,
        val startedAt: String,
        val endedAt: String,
    )
}
