package com.school_of_company.expo.viewmodel.uistate

interface RegisterStandardProgramListUiState {
    object Loading: RegisterStandardProgramListUiState
    object Success: RegisterStandardProgramListUiState
    data class Error(val exception: Throwable): RegisterStandardProgramListUiState
}