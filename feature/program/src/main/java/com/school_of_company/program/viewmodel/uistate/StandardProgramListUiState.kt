package com.school_of_company.home.viewmodel.uistate

import com.school_of_company.model.entity.standard.StandardProgramListResponseEntity

sealed interface StandardProgramListUiState {
    object Loading : StandardProgramListUiState
    object Empty : StandardProgramListUiState
    data class Success(val data: List<StandardProgramListResponseEntity>) : StandardProgramListUiState
    data class Error(val exception: Throwable) : StandardProgramListUiState
}