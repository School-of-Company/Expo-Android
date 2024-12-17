package com.school_of_company.sms.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SmsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val TITLE = "title"
        private const val CONTENT = "content"
    }

    internal var title = savedStateHandle.getStateFlow(key = TITLE, initialValue = "")

    internal var content = savedStateHandle.getStateFlow(key = CONTENT, initialValue = "")

    internal fun onTitleChange(value: String) {
        savedStateHandle[TITLE] = value
    }

    internal fun onContentChange(value: String) {
        savedStateHandle[CONTENT] = value
    }
}