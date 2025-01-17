package com.school_of_company.sms.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.domain.usecase.sms.SendSmsToParticipantTraineeUseCase
import com.school_of_company.model.param.sms.SendSmsToParticipantTraineeParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SmsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val sendSmsToParticipantTraineeUseCase: SendSmsToParticipantTraineeUseCase
) : ViewModel() {
    companion object {
        private const val TITLE = "title"
        private const val CONTENT = "content"
    }

    internal var title = savedStateHandle.getStateFlow(key = TITLE, initialValue = "")

    internal var content = savedStateHandle.getStateFlow(key = CONTENT, initialValue = "")

    internal fun sendSmsToParticipantTrainee(
        id: String,
        body: SendSmsToParticipantTraineeParam
    ) = viewModelScope.launch {
        sendSmsToParticipantTraineeUseCase(id, body)
    }

    internal fun onTitleChange(value: String) {
        savedStateHandle[TITLE] = value
    }

    internal fun onContentChange(value: String) {
        savedStateHandle[CONTENT] = value
    }
}