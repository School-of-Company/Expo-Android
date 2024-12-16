package com.school_of_company.program.viewmodel.uistate

import com.school_of_company.model.entity.training.TrainingProgramListResponseEntity

sealed interface TrainingProgramListUiState {
    object Loading : TrainingProgramListUiState
    object Empty : TrainingProgramListUiState
    data class Success(val data: List<TrainingProgramListResponseEntity>) : TrainingProgramListUiState
    data class Error(val exception: Throwable) : TrainingProgramListUiState
}