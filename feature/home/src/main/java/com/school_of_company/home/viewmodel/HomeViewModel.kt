package com.school_of_company.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.domain.usecase.expo.DeleteExpoInformationUseCase
import com.school_of_company.domain.usecase.expo.GetExpoInformationUseCase
import com.school_of_company.domain.usecase.expo.GetExpoListUseCase
import com.school_of_company.domain.usecase.expo.ModifyExpoInformationUseCase
import com.school_of_company.domain.usecase.expo.RegisterExpoInformationUseCase
import com.school_of_company.home.viewmodel.uistate.DeleteExpoInformationUiState
import com.school_of_company.home.viewmodel.uistate.GetExpoInformationUiState
import com.school_of_company.home.viewmodel.uistate.GetExpoListUiState
import com.school_of_company.home.viewmodel.uistate.ModifyExpoInformationUiState
import com.school_of_company.home.viewmodel.uistate.RegisterExpoInformationUiState
import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getExpoInformationUseCase: GetExpoInformationUseCase,
    private val registerExpoInformationUseCase: RegisterExpoInformationUseCase,
    private val modifyExpoInformationUseCase: ModifyExpoInformationUseCase,
    private val deleteExpoInformationUseCase: DeleteExpoInformationUseCase,
    private val getExpoListUseCase: GetExpoListUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val TITLE = "title"
        private const val CONTENT = "content"
    }

    private val _getExpoInformationUiState = MutableStateFlow<GetExpoInformationUiState>(GetExpoInformationUiState.Loading)
    internal val getExpoInformationUiState = _getExpoInformationUiState.asStateFlow()

    private val _registerExpoInformationUiState = MutableStateFlow<RegisterExpoInformationUiState>(RegisterExpoInformationUiState.Loading)
    internal val registerExpoInformationUiState = _registerExpoInformationUiState.asStateFlow()

    private val _modifyExpoInformationUiState = MutableStateFlow<ModifyExpoInformationUiState>(ModifyExpoInformationUiState.Loading)
    internal val modifyExpoInformationUiState = _modifyExpoInformationUiState.asStateFlow()

    private val _deleteExpoInformationUiState = MutableStateFlow<DeleteExpoInformationUiState>(DeleteExpoInformationUiState.Loading)
    internal val deleteExpoInformationUiState = _deleteExpoInformationUiState.asStateFlow()

    private val _getExpoListUiState = MutableStateFlow<GetExpoListUiState>(GetExpoListUiState.Loading)
    internal val getExpoListUiState = _getExpoListUiState.asStateFlow()


    internal var title = savedStateHandle.getStateFlow(key = TITLE, initialValue = "")

    internal var content = savedStateHandle.getStateFlow(key = CONTENT, initialValue = "")

    internal fun getExpoInformation(expoId: Long) = viewModelScope.launch {
        getExpoInformationUseCase(expoId = expoId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getExpoInformationUiState.value = GetExpoInformationUiState.Loading
                    is Result.Success -> _getExpoInformationUiState.value = GetExpoInformationUiState.Success(result.data)
                    is Result.Error -> _getExpoInformationUiState.value = GetExpoInformationUiState.Error(result.exception)
                }
            }
    }

    internal fun registerExpoInformation(body: ExpoRequestAndResponseModel) = viewModelScope.launch {
        _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Loading
        registerExpoInformationUseCase(body = body)
            .onSuccess {
                it.catch { remoteError ->
                    _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Error(remoteError)
                }.collect {
                    _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Success
                }
            }
            .onFailure { error ->
                _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Error(error)
            }
    }

    internal fun modifyExpoInformation(
        expoId: Long,
        body: ExpoRequestAndResponseModel
    ) = viewModelScope.launch {
        _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Loading
        modifyExpoInformationUseCase(
            expoId = expoId,
            body = body
        )
            .onSuccess {
                it.catch { remoteError ->
                    _modifyExpoInformationUiState.value =
                        ModifyExpoInformationUiState.Error(remoteError)
                }.collect {
                    _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Success
                }
            }
            .onFailure { error ->
                _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Error(error)
            }
    }

    internal fun deleteExpoInformation(expoId: Long) = viewModelScope.launch {
        _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Loading
        deleteExpoInformationUseCase(expoId = expoId)
            .onSuccess {
                it.catch { remoteError ->
                    _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Error(remoteError)
                }.collect {
                    _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Success

                }
            }
            .onFailure { error ->
                _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Error(error)
            }
    }

    internal fun expoList() = viewModelScope.launch {
        getExpoListUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getExpoListUiState.value = GetExpoListUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _getExpoListUiState.value = GetExpoListUiState.Empty
                        } else {
                            _getExpoListUiState.value = GetExpoListUiState.Success(result.data)
                        }
                    }
                    is Result.Error -> _getExpoListUiState.value = GetExpoListUiState.Error(result.exception)
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