package com.school_of_company.sms.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.domain.usecase.sms.SendSmsToParticipantTraineeUseCase
import com.school_of_company.model.param.sms.SendSmsToParticipantTraineeParam
import com.school_of_company.sms.viewmodel.uiState.SendSmsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SmsViewModel @Inject constructor(
    private val sendSmsToParticipantTraineeUseCase: SendSmsToParticipantTraineeUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    companion object {
        private const val TITLE = "title"
        private const val CONTENT = "content"
    }
    private val _sendSmsUiState = MutableStateFlow<SendSmsUiState>(SendSmsUiState.Loading)
    internal val sendSmsUiState = _sendSmsUiState.asStateFlow()

    internal var title = savedStateHandle.getStateFlow(key = TITLE, initialValue = "")

    internal var content = savedStateHandle.getStateFlow(key = CONTENT, initialValue = "")

    internal fun sendSmsToParticipantTrainee(
        id: String,
        body: SendSmsToParticipantTraineeParam
    ) = viewModelScope.launch {
        sendSmsToParticipantTraineeUseCase(id, body)
            .onSuccess {
                it.catch { remoteError ->
                    _sendSmsUiState.value = SendSmsUiState.Error(remoteError)
                }.collect {
                    _sendSmsUiState.value = SendSmsUiState.Success
                }
            }
            .onFailure { error ->
                _sendSmsUiState.value = SendSmsUiState.Error(error)
            }
    }

    internal fun onTitleChange(value: String) {
        savedStateHandle[TITLE] = value
    }

    internal fun onContentChange(value: String) {
        savedStateHandle[CONTENT] = value
    }
}
