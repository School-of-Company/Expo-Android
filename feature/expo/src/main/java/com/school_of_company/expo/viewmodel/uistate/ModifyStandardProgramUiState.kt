package com.school_of_company.expo.viewmodel.uistate

sealed interface ModifyStandardProgramUiState {
    object Loading : ModifyStandardProgramUiState
    object Success : ModifyStandardProgramUiState
    data class Error(val exception: Throwable) : ModifyStandardProgramUiState
}