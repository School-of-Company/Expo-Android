package com.school_of_company.expo_android

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
) : ViewModel() {
    private val _appLoginState = mutableStateOf<AppLoginState>(AppLoginState.Loading)
    val appLoginState: State<AppLoginState> = this._appLoginState
}

sealed interface AppLoginState {
    object Loading : AppLoginState
    object Success : AppLoginState
    object Fail : AppLoginState
}