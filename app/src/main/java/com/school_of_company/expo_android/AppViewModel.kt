package com.school_of_company.expo_android

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.datastore.datasource.AuthTokenDataSource
import com.school_of_company.domain.usecase.auth.AdminTokenRefreshUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val authTokenDataSource: AuthTokenDataSource,
    private val adminTokenRefreshUseCase: AdminTokenRefreshUseCase,
) : ViewModel() {
    private val _appLoginState = mutableStateOf<AppLoginState>(AppLoginState.Loading)
    internal val appLoginState: State<AppLoginState> = _appLoginState

    init {
        tokenRefresh()
    }

    private fun tokenRefresh() = viewModelScope.launch {
        _appLoginState.value = AppLoginState.Loading
        val refreshToken = authTokenDataSource.getRefreshToken().first()

        if (refreshToken.isEmpty()) {
            _appLoginState.value = AppLoginState.Fail
        } else {
            adminTokenRefreshUseCase()
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Error -> _appLoginState.value = AppLoginState.Fail
                        Result.Loading -> _appLoginState.value = AppLoginState.Loading
                        is Result.Success -> _appLoginState.value = AppLoginState.Success
                    }
                }
        }
    }
}

internal sealed interface AppLoginState {
    object Loading : AppLoginState
    object Success : AppLoginState
    object Fail : AppLoginState
}