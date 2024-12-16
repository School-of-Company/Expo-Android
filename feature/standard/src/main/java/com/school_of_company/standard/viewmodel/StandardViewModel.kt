package com.school_of_company.standard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.domain.usecase.standard.StandardProgramAttendListUseCase
import com.school_of_company.standard.viewmodel.uistate.StandardProgramAttendListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StandardViewModel @Inject constructor(
    private val standardProgramAttendListUseCase: StandardProgramAttendListUseCase
) : ViewModel() {

    private val _swipeRefreshLoading = MutableStateFlow(false)
    internal val swipeRefreshLoading = _swipeRefreshLoading.asStateFlow()

    private val _standardProgramAttendListUiState = MutableStateFlow<StandardProgramAttendListUiState>(StandardProgramAttendListUiState.Loading)
    internal val standardProgramAttendListUiState = _standardProgramAttendListUiState.asStateFlow()

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
}