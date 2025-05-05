package com.school_of_company.sms.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.data.repository.sms.SmsRepository
import com.school_of_company.model.param.sms.SendSmsToParticipantTraineeParam
import com.school_of_company.sms.viewmodel.uiState.SendSmsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SmsViewModel @Inject constructor(
    private val smsRepository: SmsRepository,
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
        smsRepository.sendSmsToParticipantTrainee(
            id = id,
            body = body
        )
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _sendSmsUiState.value = SendSmsUiState.Loading
                    is Result.Success -> _sendSmsUiState.value = SendSmsUiState.Success
                    is Result.Error -> _sendSmsUiState.value = SendSmsUiState.Error(result.exception)
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
