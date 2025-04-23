package com.school_of_company.expo.viewmodel.uistate

import com.school_of_company.model.entity.training.TrainingProgramListResponseEntity

sealed interface GetTrainingProgramListUiState {
    object Loading: GetTrainingProgramListUiState
    data class Success(val data: List<TrainingProgramListResponseEntity>): GetTrainingProgramListUiState
    data class Error(val error: Throwable): GetTrainingProgramListUiState
}