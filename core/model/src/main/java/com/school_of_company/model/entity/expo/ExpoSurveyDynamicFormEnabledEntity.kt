package com.school_of_company.model.entity.expo

data class ExpoSurveyDynamicFormEnabledEntity(
    val expoValid: List<ExpoValidityEntity>,
)

data class ExpoValidityEntity(
    val expoId: String,
    val standardFormCreatedStatus: Boolean,
    val traineeFormCreatedStatus: Boolean,
    val standardSurveyCreatedStatus: Boolean,
    val traineeSurveyCreatedStatus: Boolean,
)