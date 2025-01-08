package com.school_of_company.program.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.domain.usecase.attendance.StandardQrCodeRequestUseCase
import com.school_of_company.domain.usecase.attendance.TrainingQrCodeRequestUseCase
import com.school_of_company.domain.usecase.standard.StandardProgramListUseCase
import com.school_of_company.domain.usecase.training.TrainingProgramListUseCase
import com.school_of_company.model.param.attendance.StandardQrCodeRequestParam
import com.school_of_company.program.viewmodel.uistate.StandardProgramListUiState
import com.school_of_company.program.viewmodel.uistate.TrainingProgramListUiState
import com.school_of_company.program.viewmodel.uistate.ReadQrCodeUiState
import com.school_of_company.model.param.attendance.TrainingQrCodeRequestParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramViewModel @Inject constructor(
    private val trainingProgramListUseCase: TrainingProgramListUseCase,
    private val standardProgramListUseCase: StandardProgramListUseCase,
    private val trainingQrCodeRequestUseCase: TrainingQrCodeRequestUseCase,
    private val standardQrCodeRequestUseCase: StandardQrCodeRequestUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val TITLE = "title"
        private const val CONTENT = "content"
        private const val REQUEST_DELAY_MS = 2000L
    }

    private var isRequestInProgress = false

    private val _swipeRefreshLoading = MutableStateFlow(false)
    val swipeRefreshLoading = _swipeRefreshLoading.asStateFlow()

    private val _trainingProgramListUiState = MutableStateFlow<TrainingProgramListUiState>(TrainingProgramListUiState.Loading)
    internal val trainingProgramListUiState = _trainingProgramListUiState.asStateFlow()

    private val _standardProgramListUiState = MutableStateFlow<StandardProgramListUiState>(StandardProgramListUiState.Loading)
    internal val standardProgramListUiState = _standardProgramListUiState.asStateFlow()

    private val _readQrCodeUiState = MutableStateFlow<ReadQrCodeUiState>(ReadQrCodeUiState.Loading)
    internal val readQrCodeUiState = _readQrCodeUiState.asStateFlow()

    internal var title = savedStateHandle.getStateFlow(key = TITLE, initialValue = "")
    internal var content = savedStateHandle.getStateFlow(key = CONTENT, initialValue = "")

    internal fun trainingProgramList(expoId: String) = viewModelScope.launch {
        _swipeRefreshLoading.value = true
        trainingProgramListUseCase(expoId = expoId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _trainingProgramListUiState.value =
                        TrainingProgramListUiState.Loading

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
                    is Result.Loading -> _standardProgramListUiState.value =
                        StandardProgramListUiState.Loading

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

    internal fun trainingQrCode(
        trainingId: Long,
        body: TrainingQrCodeRequestParam
    ) {
        if (!isRequestInProgress) {
            isRequestInProgress = true

            viewModelScope.launch {

                _readQrCodeUiState.value = ReadQrCodeUiState.Loading
                try {
                    trainingQrCodeRequestUseCase(
                        trainingId = trainingId,
                        body = body
                    )
                        .onSuccess {
                            it.catch { remoteError ->
                                _readQrCodeUiState.value = ReadQrCodeUiState.Error(remoteError)
                            }.collect {
                                _readQrCodeUiState.value = ReadQrCodeUiState.Success
                            }
                            delay(REQUEST_DELAY_MS)
                        }
                        .onFailure { error ->
                            _readQrCodeUiState.value = ReadQrCodeUiState.Error(error)
                        }
                } finally {
                    isRequestInProgress = false
                }
            }
        }
    }

    internal fun standardQrCode(
        standardId: Long,
        body: StandardQrCodeRequestParam,
    ) {
        if (!isRequestInProgress) {
            isRequestInProgress = true

            viewModelScope.launch {

                _readQrCodeUiState.value = ReadQrCodeUiState.Loading
                try {
                    standardQrCodeRequestUseCase(
                        standardId = standardId,
                        body = body
                    )
                        .onSuccess {
                            it.catch { remoteError ->
                                _readQrCodeUiState.value = ReadQrCodeUiState.Error(remoteError)
                            }.collect {
                                _readQrCodeUiState.value = ReadQrCodeUiState.Success
                            }
                            delay(REQUEST_DELAY_MS)
                        }
                        .onFailure { error ->
                            _readQrCodeUiState.value = ReadQrCodeUiState.Error(error)
                        }
                } finally {
                    isRequestInProgress = false
                }
            }
        }
    }

    internal fun onTitleChange(value: String) {
        savedStateHandle[TITLE] = value
    }

    internal fun onContentChange(value: String) {
        savedStateHandle[CONTENT] = value
    }
}