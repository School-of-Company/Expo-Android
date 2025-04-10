package com.school_of_company.domain.usecase.expo

import com.school_of_company.data.repository.expo.ExpoRepository
import com.school_of_company.model.entity.expo.ExpoSurveyDynamicFormEnabledEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckExpoSurveyDynamicFormEnableUseCase @Inject constructor(
    private val repository: ExpoRepository
) {
    operator fun invoke(): Flow<ExpoSurveyDynamicFormEnabledEntity> =
        repository.checkExpoSurveyDynamicFormEnable()
}