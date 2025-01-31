package com.school_of_company.user.viewmodel.uistate

import com.school_of_company.model.entity.admin.AdminInformationResponseEntity

sealed interface GetAdminInformationUiState {
    object Loading : GetAdminInformationUiState
    data class Success(val data: AdminInformationResponseEntity) : GetAdminInformationUiState
    data class Error(val exception: Throwable) : GetAdminInformationUiState
}