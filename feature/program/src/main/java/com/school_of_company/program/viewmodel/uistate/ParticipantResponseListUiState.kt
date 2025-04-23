package com.school_of_company.program.viewmodel.uistate

import com.school_of_company.model.entity.participant.ParticipantInformationResponseEntity

sealed interface ParticipantResponseListUiState {
    object Loading : ParticipantResponseListUiState
    object Empty : ParticipantResponseListUiState
    data class Success(val data: ParticipantInformationResponseEntity) : ParticipantResponseListUiState
    data class Error(val exception: Throwable) : ParticipantResponseListUiState
}