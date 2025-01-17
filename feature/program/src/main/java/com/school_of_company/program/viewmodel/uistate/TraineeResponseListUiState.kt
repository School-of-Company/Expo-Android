package com.school_of_company.program.viewmodel.uistate

import com.school_of_company.model.entity.trainee.TraineeResponseEntity

sealed interface TraineeResponseListUiState {
    object Loading : TraineeResponseListUiState
    object Empty : TraineeResponseListUiState
    data class Success(val data: List<TraineeResponseEntity>) : TraineeResponseListUiState
    data class Error(val exception: Throwable) : TraineeResponseListUiState
}