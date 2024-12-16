package com.school_of_company.program.viewmodel.uistate

import com.school_of_company.model.entity.training.TeacherTrainingProgramResponseEntity

sealed interface TeacherTrainingProgramListUiState {
    object Loading: TeacherTrainingProgramListUiState
    object Empty: TeacherTrainingProgramListUiState
    data class Success(val data: List<TeacherTrainingProgramResponseEntity>): TeacherTrainingProgramListUiState
    data class Error(val exception: Throwable): TeacherTrainingProgramListUiState
}