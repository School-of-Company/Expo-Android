package com.school_of_company.expo.viewmodel.uistate

import com.school_of_company.model.entity.expo.ExpoSurveyDynamicFormEnabledEntity

sealed interface CheckExpoSurveyDynamicFormEnableUiState {
    object Loading : CheckExpoSurveyDynamicFormEnableUiState
    data class Success(val data: ExpoSurveyDynamicFormEnabledEntity) : CheckExpoSurveyDynamicFormEnableUiState
    data class Error(val exception: Throwable) : CheckExpoSurveyDynamicFormEnableUiState
}