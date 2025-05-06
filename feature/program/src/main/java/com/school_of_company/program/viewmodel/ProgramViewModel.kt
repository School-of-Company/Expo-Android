package com.school_of_company.program.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.data.repository.attendance.AttendanceRepository
import com.school_of_company.data.repository.participant.ParticipantRepository
import com.school_of_company.data.repository.standard.StandardRepository
import com.school_of_company.domain.usecase.trainee.TraineeResponseListUseCase
import com.school_of_company.domain.usecase.training.TrainingProgramListUseCase
import com.school_of_company.model.param.attendance.StandardQrCodeRequestParam
import com.school_of_company.model.param.attendance.TrainingQrCodeRequestParam
import com.school_of_company.program.viewmodel.uistate.ParticipantResponseListUiState
import com.school_of_company.program.viewmodel.uistate.ReadQrCodeUiState
import com.school_of_company.program.viewmodel.uistate.StandardProgramListUiState
import com.school_of_company.program.viewmodel.uistate.TraineeResponseListUiState
import com.school_of_company.program.viewmodel.uistate.TrainingProgramListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProgramViewModel @Inject constructor(
    private val trainingProgramListUseCase: TrainingProgramListUseCase,
    private val standardRepository: StandardRepository,
    private val traineeResponseListUseCase: TraineeResponseListUseCase,
    private val attendanceRepository: AttendanceRepository,
    private val participantRepository: ParticipantRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val REQUEST_DELAY_MS = 2000L
        private const val TRAINEE_NAME = "trainee_name"
        private const val CURRENT_PAGE = "current_page"
        private const val PAGE_SIZE = 75
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

    private val _traineeResponseListUiState = MutableStateFlow<TraineeResponseListUiState>(TraineeResponseListUiState.Loading)
    internal val traineeResponseListUiState = _traineeResponseListUiState.asStateFlow()

    private val _participantListUiState = MutableStateFlow<ParticipantResponseListUiState>(ParticipantResponseListUiState.Loading)
    internal val participantListUiState = _participantListUiState.asStateFlow()

    internal val traineeName = savedStateHandle.getStateFlow(TRAINEE_NAME, "")
    internal val currentPage = savedStateHandle.getStateFlow(CURRENT_PAGE, 0)

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
                            _trainingProgramListUiState.value =
                                TrainingProgramListUiState.Success(result.data)
                            _swipeRefreshLoading.value = false
                        }
                    }

                    is Result.Error -> {
                        _trainingProgramListUiState.value =
                            TrainingProgramListUiState.Error(result.exception)
                        _swipeRefreshLoading.value = false
                    }
                }
            }
    }

    internal fun standardProgramList(expoId: String) = viewModelScope.launch {
        _swipeRefreshLoading.value = true
        standardRepository.standardProgramList(expoId = expoId)
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

    internal fun trainingQrCode(
        trainingId: Long,
        body: TrainingQrCodeRequestParam
    ) {
        if (!isRequestInProgress) {
            isRequestInProgress = true

            viewModelScope.launch {
                try {
                    _readQrCodeUiState.value = ReadQrCodeUiState.Loading
                    attendanceRepository.trainingQrCode(
                        trainingId = trainingId,
                        body = body
                    )
                        .asResult()
                        .collectLatest { result ->
                            when (result) {
                                is Result.Loading -> _readQrCodeUiState.value = ReadQrCodeUiState.Loading
                                is Result.Success -> {
                                    _readQrCodeUiState.value = ReadQrCodeUiState.Success
                                    delay(REQUEST_DELAY_MS)
                                }
                                is Result.Error -> _readQrCodeUiState.value = ReadQrCodeUiState.Error(result.exception)
                            }
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
                    attendanceRepository.standardQrCode(
                        standardId = standardId,
                        body = body
                    )
                        .asResult()
                        .collectLatest { result ->
                            when (result) {
                                is Result.Loading -> _readQrCodeUiState.value = ReadQrCodeUiState.Loading
                                is Result.Success -> {
                                    _readQrCodeUiState.value = ReadQrCodeUiState.Success
                                    delay(REQUEST_DELAY_MS)
                                }
                                is Result.Error -> _readQrCodeUiState.value = ReadQrCodeUiState.Error(result.exception)
                            }
                        }
                } finally {
                    isRequestInProgress = false
                }
            }
        }
    }

    internal fun getTraineeList(
        expoId: String,
        name: String? = null
    ) = viewModelScope.launch {
        _swipeRefreshLoading.value = true
        traineeResponseListUseCase(
            expoId = expoId,
            name = name
        )
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _traineeResponseListUiState.value = TraineeResponseListUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _swipeRefreshLoading.value = false
                            _traineeResponseListUiState.value = TraineeResponseListUiState.Empty
                        } else {
                            _swipeRefreshLoading.value = false
                            _traineeResponseListUiState.value = TraineeResponseListUiState.Success(result.data)
                        }
                    }
                    is Result.Error -> {
                        _swipeRefreshLoading.value = false
                        _traineeResponseListUiState.value = TraineeResponseListUiState.Error(result.exception)
                    }
                }
            }
    }

    internal fun getParticipantInformationList(
        expoId: String,
        currentPage: Int,
        localDate: String? = null
    ) = viewModelScope.launch {
        _swipeRefreshLoading.value = true
        participantRepository.getParticipantInformationList(
            expoId = expoId,
            localDate = localDate,
            page = currentPage,
            size = PAGE_SIZE
        )
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _participantListUiState.value = ParticipantResponseListUiState.Loading
                    is Result.Success -> {
                        if (result.data.participant.isEmpty()) {
                            _swipeRefreshLoading.value = false
                            _participantListUiState.value = ParticipantResponseListUiState.Empty
                        } else {
                            _swipeRefreshLoading.value = false
                            _participantListUiState.value = ParticipantResponseListUiState.Success(result.data)
                        }
                    }
                    is Result.Error -> {
                        _swipeRefreshLoading.value = false
                        _participantListUiState.value = ParticipantResponseListUiState.Error(result.exception)
                    }
                }
            }
    }

    internal fun onTraineeNameChange(value: String) {
        savedStateHandle[TRAINEE_NAME] = value
    }

    internal fun onCurrentPageChange(value: Int) {
        savedStateHandle[CURRENT_PAGE] = value
    }
}