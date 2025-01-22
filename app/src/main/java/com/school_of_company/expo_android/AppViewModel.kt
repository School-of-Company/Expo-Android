package com.school_of_company.expo_android

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.datastore.datasource.AuthTokenDataSource
import com.school_of_company.domain.usecase.auth.AdminTokenRefreshUseCase
import com.school_of_company.domain.usecase.auth.SaveTokenUseCase
import com.school_of_company.model.model.auth.AdminTokenResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val authTokenDataSource: AuthTokenDataSource,
    private val saveTokenUseCase: SaveTokenUseCase,
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
                .onSuccess {
                    it.catch {
                        _appLoginState.value = AppLoginState.Fail
                    }.collect { result ->
                        saveToken(result)
                    }
                }
                .onFailure {
                    _appLoginState.value = AppLoginState.Fail
                }

        }
    }

    private suspend fun saveToken(token: AdminTokenResponseModel) =
        saveTokenUseCase(token)
            .onSuccess {
                _appLoginState.value = AppLoginState.Success
            }
            .onFailure {
                _appLoginState.value = AppLoginState.Fail
            }

}

internal sealed interface AppLoginState {
    object Loading : AppLoginState
    object Success : AppLoginState
    object Fail : AppLoginState
}