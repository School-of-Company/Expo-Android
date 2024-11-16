package com.school_of_company.expo.viewmodel

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
import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpoViewModel @Inject constructor(
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
        private const val MODIFY_TITLE = "modify_title"
        private const val STARTED_DATE = "started_date"
        private const val ENDED_DATE = "ended_date"
        private const val INTRODUCE_TITLE = "introduce_title"
        private const val ADDRESS = "address"
        private const val LOCATION = "location"
    }

    private val _getExpoInformationUiState = MutableStateFlow<com.school_of_company.expo.viewmodel.uistate.GetExpoInformationUiState>(
        com.school_of_company.expo.viewmodel.uistate.GetExpoInformationUiState.Loading)
    internal val getExpoInformationUiState = _getExpoInformationUiState.asStateFlow()

    private val _registerExpoInformationUiState = MutableStateFlow<com.school_of_company.expo.viewmodel.uistate.RegisterExpoInformationUiState>(
        com.school_of_company.expo.viewmodel.uistate.RegisterExpoInformationUiState.Loading)
    internal val registerExpoInformationUiState = _registerExpoInformationUiState.asStateFlow()

    private val _modifyExpoInformationUiState = MutableStateFlow<com.school_of_company.expo.viewmodel.uistate.ModifyExpoInformationUiState>(
        com.school_of_company.expo.viewmodel.uistate.ModifyExpoInformationUiState.Loading)
    internal val modifyExpoInformationUiState = _modifyExpoInformationUiState.asStateFlow()

    private val _deleteExpoInformationUiState = MutableStateFlow<com.school_of_company.expo.viewmodel.uistate.DeleteExpoInformationUiState>(
        com.school_of_company.expo.viewmodel.uistate.DeleteExpoInformationUiState.Loading)
    internal val deleteExpoInformationUiState = _deleteExpoInformationUiState.asStateFlow()

    private val _getExpoListUiState = MutableStateFlow<com.school_of_company.expo.viewmodel.uistate.GetExpoListUiState>(
        com.school_of_company.expo.viewmodel.uistate.GetExpoListUiState.Loading)
    internal val getExpoListUiState = _getExpoListUiState.asStateFlow()


    internal var title = savedStateHandle.getStateFlow(key = TITLE, initialValue = "")

    internal var content = savedStateHandle.getStateFlow(key = CONTENT, initialValue = "")

    internal var modify_title = savedStateHandle.getStateFlow(key = MODIFY_TITLE, initialValue = "")

    internal var started_date = savedStateHandle.getStateFlow(key = STARTED_DATE, initialValue = "")

    internal var ended_date = savedStateHandle.getStateFlow(key = ENDED_DATE, initialValue = "")

    internal var introduce_title = savedStateHandle.getStateFlow(key = INTRODUCE_TITLE, initialValue = "")

    internal var address = savedStateHandle.getStateFlow(key = ADDRESS, initialValue = "")

    internal var location = savedStateHandle.getStateFlow(key = LOCATION, initialValue = "")

    internal fun getExpoInformation(expoId: Long) = viewModelScope.launch {
        getExpoInformationUseCase(expoId = expoId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getExpoInformationUiState.value = com.school_of_company.expo.viewmodel.uistate.GetExpoInformationUiState.Loading
                    is Result.Success -> _getExpoInformationUiState.value = com.school_of_company.expo.viewmodel.uistate.GetExpoInformationUiState.Success(result.data)
                    is Result.Error -> _getExpoInformationUiState.value = com.school_of_company.expo.viewmodel.uistate.GetExpoInformationUiState.Error(result.exception)
                }
            }
    }

    internal fun registerExpoInformation(body: ExpoRequestAndResponseModel) = viewModelScope.launch {
        _registerExpoInformationUiState.value = com.school_of_company.expo.viewmodel.uistate.RegisterExpoInformationUiState.Loading
        registerExpoInformationUseCase(body = body)
            .onSuccess {
                it.catch { remoteError ->
                    _registerExpoInformationUiState.value = com.school_of_company.expo.viewmodel.uistate.RegisterExpoInformationUiState.Error(remoteError)
                }.collect {
                    _registerExpoInformationUiState.value = com.school_of_company.expo.viewmodel.uistate.RegisterExpoInformationUiState.Success
                }
            }
            .onFailure { error ->
                _registerExpoInformationUiState.value = com.school_of_company.expo.viewmodel.uistate.RegisterExpoInformationUiState.Error(error)
            }
    }

    internal fun modifyExpoInformation(
        expoId: Long,
        body: ExpoRequestAndResponseModel
    ) = viewModelScope.launch {
        _modifyExpoInformationUiState.value = com.school_of_company.expo.viewmodel.uistate.ModifyExpoInformationUiState.Loading
        modifyExpoInformationUseCase(
            expoId = expoId,
            body = body
        )
            .onSuccess {
                it.catch { remoteError ->
                    _modifyExpoInformationUiState.value =
                        com.school_of_company.expo.viewmodel.uistate.ModifyExpoInformationUiState.Error(remoteError)
                }.collect {
                    _modifyExpoInformationUiState.value = com.school_of_company.expo.viewmodel.uistate.ModifyExpoInformationUiState.Success
                }
            }
            .onFailure { error ->
                _modifyExpoInformationUiState.value = com.school_of_company.expo.viewmodel.uistate.ModifyExpoInformationUiState.Error(error)
            }
    }

    internal fun deleteExpoInformation(expoId: Long) = viewModelScope.launch {
        _deleteExpoInformationUiState.value = com.school_of_company.expo.viewmodel.uistate.DeleteExpoInformationUiState.Loading
        deleteExpoInformationUseCase(expoId = expoId)
            .onSuccess {
                it.catch { remoteError ->
                    _deleteExpoInformationUiState.value = com.school_of_company.expo.viewmodel.uistate.DeleteExpoInformationUiState.Error(remoteError)
                }.collect {
                    _deleteExpoInformationUiState.value = com.school_of_company.expo.viewmodel.uistate.DeleteExpoInformationUiState.Success

                }
            }
            .onFailure { error ->
                _deleteExpoInformationUiState.value = com.school_of_company.expo.viewmodel.uistate.DeleteExpoInformationUiState.Error(error)
            }
    }

    internal fun expoList() = viewModelScope.launch {
        getExpoListUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getExpoListUiState.value = com.school_of_company.expo.viewmodel.uistate.GetExpoListUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _getExpoListUiState.value = com.school_of_company.expo.viewmodel.uistate.GetExpoListUiState.Empty
                        } else {
                            _getExpoListUiState.value = com.school_of_company.expo.viewmodel.uistate.GetExpoListUiState.Success(result.data)
                        }
                    }
                    is Result.Error -> _getExpoListUiState.value = com.school_of_company.expo.viewmodel.uistate.GetExpoListUiState.Error(result.exception)
                }
            }
    }

    internal fun onTitleChange(value: String) {
        savedStateHandle[TITLE] = value
    }

    internal fun onContentChange(value: String) {
        savedStateHandle[CONTENT] = value
    }

    internal fun onModifyTitleChange(value: String) {
        savedStateHandle[MODIFY_TITLE] = value
    }

    internal fun onStartedDateChange(value: String) {
        savedStateHandle[STARTED_DATE] = value
    }

    internal fun onEndedDateChange(value: String) {
        savedStateHandle[ENDED_DATE] = value
    }

    internal fun onIntroduceTitleChange(value: String) {
        savedStateHandle[INTRODUCE_TITLE] = value
    }

    internal fun onAddressChange(value: String) {
        savedStateHandle[ADDRESS] = value
    }

    internal fun onLocationChange(value: String) {
        savedStateHandle[LOCATION] = value
    }
}