package com.school_of_company.home.viewmodel.uistate

import com.school_of_company.model.entity.standard.StandardAttendListResponseEntity

sealed interface StandardProgramAttendListUiState {
    object Loading : StandardProgramAttendListUiState
    object Empty : StandardProgramAttendListUiState
    data class Success(val data: List<StandardAttendListResponseEntity>) : StandardProgramAttendListUiState
    data class Error(val exception: Throwable) : StandardProgramAttendListUiState
}