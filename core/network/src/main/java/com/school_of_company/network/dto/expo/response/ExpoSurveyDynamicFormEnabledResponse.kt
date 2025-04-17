package com.school_of_company.network.dto.expo.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExpoSurveyDynamicFormEnabledResponse(
    @Json(name = "expoValid") val expoValid: List<ExpoValidityResponse>,
)

@JsonClass(generateAdapter = true)
data class ExpoValidityResponse(
    @Json(name = "expoId") val expoId: String,
    @Json(name = "standardFormCreatedStatus") val standardFormCreatedStatus: Boolean,
    @Json(name = "traineeFormCreatedStatus") val traineeFormCreatedStatus: Boolean,
)