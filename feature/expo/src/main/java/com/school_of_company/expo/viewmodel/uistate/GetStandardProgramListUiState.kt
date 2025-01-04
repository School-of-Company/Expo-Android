package com.school_of_company.expo.viewmodel.uistate

import com.school_of_company.model.entity.standard.StandardProgramListResponseEntity

sealed interface GetStandardProgramListUiState {
    object Loading: GetStandardProgramListUiState
    data class Success(val data: List<StandardProgramListResponseEntity>): GetStandardProgramListUiState
    data class Error(val exception: Throwable): GetStandardProgramListUiState
}