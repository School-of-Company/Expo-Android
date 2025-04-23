package com.school_of_company.user.viewmodel.uistate

import com.school_of_company.model.entity.admin.AdminRequestAllowListResponseEntity

sealed interface GetAdminRequestAllowListUiState {
    object Loading : GetAdminRequestAllowListUiState
    object Empty : GetAdminRequestAllowListUiState
    data class Success(val data: List<AdminRequestAllowListResponseEntity>) : GetAdminRequestAllowListUiState
    data class Error(val exception: Throwable) : GetAdminRequestAllowListUiState
}