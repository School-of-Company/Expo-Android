package com.school_of_company.expo.viewmodel.uistate

sealed interface ModifyTrainingProgramUiState {
    object Loading : ModifyTrainingProgramUiState
    object Success : ModifyTrainingProgramUiState
    data class Error(val exception: Throwable) : ModifyTrainingProgramUiState
}