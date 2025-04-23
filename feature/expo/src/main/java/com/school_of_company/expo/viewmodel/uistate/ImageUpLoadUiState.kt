package com.school_of_company.expo.viewmodel.uistate

import com.school_of_company.model.entity.image.ImageUpLoadResponseEntity

sealed interface ImageUpLoadUiState {
    object Loading : ImageUpLoadUiState
    data class Success(val data: ImageUpLoadResponseEntity) : ImageUpLoadUiState
    data class Error(val exception: Throwable) : ImageUpLoadUiState
}