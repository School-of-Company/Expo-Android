package com.school_of_company.standard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.domain.usecase.attendance.StandardQrCodeRequestUseCase
import com.school_of_company.domain.usecase.standard.StandardProgramAttendListUseCase
import com.school_of_company.model.param.attendance.StandardQrCodeRequestParam
import com.school_of_company.standard.viewmodel.uistate.StandardProgramAttendListUiState
import com.school_of_company.standard.viewmodel.uistate.StandardQrCodeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class StandardViewModel @Inject constructor(
    private val standardQrCodeRequestUseCase: StandardQrCodeRequestUseCase,
    private val standardProgramAttendListUseCase: StandardProgramAttendListUseCase,
) : ViewModel() {

    private val _swipeRefreshLoading = MutableStateFlow(false)
    internal val swipeRefreshLoading = _swipeRefreshLoading.asStateFlow()

    private val _standardProgramAttendListUiState = MutableStateFlow<StandardProgramAttendListUiState>(StandardProgramAttendListUiState.Loading)
    internal val standardProgramAttendListUiState = _standardProgramAttendListUiState.asStateFlow()

    // TODO: 사용 여부 확인
    private val _standardQrCodeUiState = MutableStateFlow<StandardQrCodeUiState>(StandardQrCodeUiState.Loading)
    internal val standardQrCodeUiState = _standardQrCodeUiState.asStateFlow()

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
    // TODO: 사용 여부 확인

    internal fun standardQrCode(
        standardProId: Long,
        body: StandardQrCodeRequestParam
    ) = viewModelScope.launch {
        _standardQrCodeUiState.value = StandardQrCodeUiState.Loading
        standardQrCodeRequestUseCase(
            standardId = standardProId,
            body = body
        )
            .onSuccess {
                it.catch { remoteError ->
                    _standardQrCodeUiState.value = StandardQrCodeUiState.Error(remoteError)
                }.collect {
                    _standardQrCodeUiState.value = StandardQrCodeUiState.Success
                }
            }
            .onFailure { error ->
                _standardQrCodeUiState.value = StandardQrCodeUiState.Error(error)
            }
    }
}