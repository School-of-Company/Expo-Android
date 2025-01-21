package com.school_of_company.expo_android

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = mutableStateOf<AppLoginState>(AppLoginState.Loading)
    val uiState: State<AppLoginState> = _uiState
}

sealed interface AppLoginState {
    object Loading : AppLoginState
    object Success : AppLoginState
    object Fail : AppLoginState
}