package com.school_of_company.network.mapper.expo.response

import com.school_of_company.model.entity.expo.ExpoSurveyDynamicFormEnabledEntity
import com.school_of_company.model.entity.expo.ExpoValidityEntity
import com.school_of_company.network.dto.expo.response.ExpoSurveyDynamicFormEnabledResponse
import com.school_of_company.network.dto.expo.response.ExpoValidityResponse

fun ExpoSurveyDynamicFormEnabledResponse.toEntity(): ExpoSurveyDynamicFormEnabledEntity =
    ExpoSurveyDynamicFormEnabledEntity(
        expoValid = this.expoValid.map { it.toEntity() }
    )

fun ExpoValidityResponse.toEntity(): ExpoValidityEntity =
    ExpoValidityEntity(
        expoId = this.expoId,
        standardFormCreatedStatus = this.standardFormCreatedStatus,
        traineeFormCreatedStatus = this.traineeFormCreatedStatus,
    )