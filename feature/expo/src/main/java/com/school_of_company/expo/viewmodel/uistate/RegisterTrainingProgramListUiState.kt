package com.school_of_company.expo.viewmodel.uistate

sealed interface RegisterTrainingProgramListUiState {
    object Loading : RegisterTrainingProgramListUiState
    object Success : RegisterTrainingProgramListUiState
    data class Error(val exception: Throwable) : RegisterTrainingProgramListUiState
}