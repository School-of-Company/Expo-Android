package com.school_of_company.training.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.data.repository.training.TrainingRepository
import com.school_of_company.training.viewmodel.uistate.TeacherTrainingProgramListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TrainingViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository
) : ViewModel() {
    private val _swipeRefreshLoading = MutableStateFlow(false)
    internal val swipeRefreshLoading = _swipeRefreshLoading.asStateFlow()

    private val _teacherTrainingProgramListUiState = MutableStateFlow<TeacherTrainingProgramListUiState>(TeacherTrainingProgramListUiState.Loading)
    internal val teacherTrainingProgramListUiState = _teacherTrainingProgramListUiState.asStateFlow()

    internal fun teacherTrainingProgramList(trainingProId: Long) = viewModelScope.launch {
        _teacherTrainingProgramListUiState.value = TeacherTrainingProgramListUiState.Loading
        trainingRepository.teacherTrainingProgramList(trainingProId = trainingProId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _teacherTrainingProgramListUiState.value = TeacherTrainingProgramListUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _teacherTrainingProgramListUiState.value = TeacherTrainingProgramListUiState.Empty
                        } else {
                            _teacherTrainingProgramListUiState.value = TeacherTrainingProgramListUiState.Success(result.data)
                        }
                    }
                    is Result.Error -> {
                        _teacherTrainingProgramListUiState.value = TeacherTrainingProgramListUiState.Error(result.exception)
                    }
                }
            }
    }
}