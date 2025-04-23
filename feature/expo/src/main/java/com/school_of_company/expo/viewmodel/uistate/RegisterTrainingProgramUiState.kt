package com.school_of_company.expo.viewmodel.uistate

sealed interface RegisterTrainingProgramUiState {
    object Loading : RegisterTrainingProgramUiState
    object Success : RegisterTrainingProgramUiState
    data class Error(val exception: Throwable) : RegisterTrainingProgramUiState
}