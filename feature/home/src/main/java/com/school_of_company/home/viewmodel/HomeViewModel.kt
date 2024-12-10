package com.school_of_company.home.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.domain.usecase.attendance.TrainingQrCodeRequestUseCase
import com.school_of_company.domain.usecase.standard.StandardProgramAttendListUseCase
import com.school_of_company.domain.usecase.standard.StandardProgramListUseCase
import com.school_of_company.domain.usecase.training.TeacherTrainingProgramListUseCase
import com.school_of_company.domain.usecase.training.TrainingProgramListUseCase
import com.school_of_company.home.viewmodel.uistate.StandardProgramAttendListUiState
import com.school_of_company.home.viewmodel.uistate.StandardProgramListUiState
import com.school_of_company.home.viewmodel.uistate.TeacherTrainingProgramListUiState
import com.school_of_company.home.viewmodel.uistate.TrainingProgramListUiState
import com.school_of_company.home.viewmodel.uistate.TrainingQrCodeUiState
import com.school_of_company.model.param.attendance.TrainingQrCodeRequestParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val trainingProgramListUseCase: TrainingProgramListUseCase,
    private val standardProgramListUseCase: StandardProgramListUseCase,
    private val teacherTrainingProgramListUseCase: TeacherTrainingProgramListUseCase,
    private val standardProgramAttendListUseCase: StandardProgramAttendListUseCase,
    private val trainingQrCodeRequestUseCase: TrainingQrCodeRequestUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val TITLE = "title"
        private const val CONTENT = "content"
    }

    private val _swipeRefreshLoading = MutableStateFlow(false)
    val swipeRefreshLoading = _swipeRefreshLoading.asStateFlow()

    private val _trainingProgramListUiState = MutableStateFlow<TrainingProgramListUiState>(TrainingProgramListUiState.Loading)
    internal val trainingProgramListUiState = _trainingProgramListUiState.asStateFlow()

    private val _standardProgramListUiState = MutableStateFlow<StandardProgramListUiState>(StandardProgramListUiState.Loading)
    internal val standardProgramListUiState = _standardProgramListUiState.asStateFlow()

    private val _teacherTrainingProgramListUiState = MutableStateFlow<TeacherTrainingProgramListUiState>(TeacherTrainingProgramListUiState.Loading)
    internal val teacherTrainingProgramListUiState = _teacherTrainingProgramListUiState.asStateFlow()

    private val _standardProgramAttendListUiState = MutableStateFlow<StandardProgramAttendListUiState>(StandardProgramAttendListUiState.Loading)
    internal val standardProgramAttendListUiState = _standardProgramAttendListUiState.asStateFlow()

    private val _trainingQrCodeUiState = MutableStateFlow<TrainingQrCodeUiState>(TrainingQrCodeUiState.Loading)
    internal val trainingQrCodeUiState = _trainingQrCodeUiState.asStateFlow()

    internal var title = savedStateHandle.getStateFlow(key = TITLE, initialValue = "")

    internal var content = savedStateHandle.getStateFlow(key = CONTENT, initialValue = "")

    internal fun trainingProgramList(expoId: String) = viewModelScope.launch {
        _swipeRefreshLoading.value = true
        trainingProgramListUseCase(expoId = expoId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _trainingProgramListUiState.value = TrainingProgramListUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _trainingProgramListUiState.value = TrainingProgramListUiState.Empty
                            _swipeRefreshLoading.value = false
                        } else {
                            _trainingProgramListUiState.value = TrainingProgramListUiState.Success(result.data)
                            _swipeRefreshLoading.value = false
                        }
                    }
                    is Result.Error -> {
                        _trainingProgramListUiState.value = TrainingProgramListUiState.Error(result.exception)
                        _swipeRefreshLoading.value = false
                    }
                }
            }
    }

    internal fun standardProgramList(expoId: String) = viewModelScope.launch {
        _swipeRefreshLoading.value = true
        standardProgramListUseCase(expoId = expoId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _standardProgramListUiState.value = StandardProgramListUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _standardProgramListUiState.value = StandardProgramListUiState.Empty
                            _swipeRefreshLoading.value = false
                        } else {
                            _standardProgramListUiState.value = StandardProgramListUiState.Success(result.data)
                            _swipeRefreshLoading.value = false
                        }
                    }
                    is Result.Error -> {
                        _standardProgramListUiState.value = StandardProgramListUiState.Error(result.exception)
                        _swipeRefreshLoading.value = false
                    }
                }
            }
    }

    internal fun teacherTrainingProgramList(trainingProId: Long) = viewModelScope.launch {
        _teacherTrainingProgramListUiState.value = TeacherTrainingProgramListUiState.Loading
        teacherTrainingProgramListUseCase(trainingProId = trainingProId)
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

    internal fun standardProgramList(standardProId: Long) = viewModelScope.launch {
        _swipeRefreshLoading.value = true
        standardProgramAttendListUseCase(standardProId = standardProId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _standardProgramAttendListUiState.value = StandardProgramAttendListUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _standardProgramAttendListUiState.value = StandardProgramAttendListUiState.Empty
                            _swipeRefreshLoading.value = false
                        } else {
                            _standardProgramAttendListUiState.value = StandardProgramAttendListUiState.Success(result.data)
                            _swipeRefreshLoading.value = false
                        }
                    }
                    is Result.Error -> {
                        _standardProgramAttendListUiState.value = StandardProgramAttendListUiState.Error(result.exception)
                        _swipeRefreshLoading.value = false
                    }
                }
            }
    }

    internal fun trainingQrCode(
        trainingId: Long,
        body: TrainingQrCodeRequestParam
    ) = viewModelScope.launch {
        _trainingQrCodeUiState.value = TrainingQrCodeUiState.Loading
        trainingQrCodeRequestUseCase(
            trainingId = trainingId,
            body = body
        )
            .onSuccess {
                it.catch { remoteError ->
                    _trainingQrCodeUiState.value = TrainingQrCodeUiState.Error(remoteError)
                }.collect {
                    _trainingQrCodeUiState.value = TrainingQrCodeUiState.Success
                }
            }
            .onFailure { error ->
                _trainingQrCodeUiState.value = TrainingQrCodeUiState.Error(error)
            }
    }

    internal fun onTitleChange(value: String) {
        savedStateHandle[TITLE] = value
    }

    internal fun onContentChange(value: String) {
        savedStateHandle[CONTENT] = value
    }
}