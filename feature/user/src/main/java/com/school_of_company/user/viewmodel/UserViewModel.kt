package com.school_of_company.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.domain.usecase.admin.AllowAdminRequestUseCase
import com.school_of_company.domain.usecase.admin.GetAdminRequestAllowListUseCase
import com.school_of_company.domain.usecase.admin.RejectAdminRequestUseCase
import com.school_of_company.domain.usecase.admin.ServiceWithdrawalUseCase
import com.school_of_company.domain.usecase.auth.AdminLogoutUseCase
import com.school_of_company.user.viewmodel.uistate.AllowAdminRequestUiState
import com.school_of_company.user.viewmodel.uistate.GetAdminRequestAllowListUiState
import com.school_of_company.user.viewmodel.uistate.LogoutUiState
import com.school_of_company.user.viewmodel.uistate.RejectAdminRequestUiState
import com.school_of_company.user.viewmodel.uistate.ServiceWithdrawalUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val logoutUseCase: AdminLogoutUseCase,
    private val allowAdminRequestUseCase: AllowAdminRequestUseCase,
    private val rejectAdminRequestUseCase: RejectAdminRequestUseCase,
    private val serviceWithdrawalUseCase: ServiceWithdrawalUseCase,
    private val getAdminRequestAllowListUseCase: GetAdminRequestAllowListUseCase
) : ViewModel() {

    private val _swipeRefreshLoading = MutableStateFlow(false)
    val swipeRefreshLoading = _swipeRefreshLoading.asStateFlow()

    private val _getAdminRequestAllowListUiState = MutableStateFlow<GetAdminRequestAllowListUiState>(GetAdminRequestAllowListUiState.Loading)
    internal val getAdminRequestAllowListUiState = _getAdminRequestAllowListUiState.asStateFlow()

    private val _allowAdminRequestUiState = MutableStateFlow<AllowAdminRequestUiState>(AllowAdminRequestUiState.Loading)
    internal val allowAdminRequestUiState = _allowAdminRequestUiState.asStateFlow()

    private val _rejectAdminRequestUiState = MutableStateFlow<RejectAdminRequestUiState>(RejectAdminRequestUiState.Loading)
    internal val rejectAdminRequestUiState = _rejectAdminRequestUiState.asStateFlow()

    private val _serviceWithdrawalUiState = MutableStateFlow<ServiceWithdrawalUiState>(ServiceWithdrawalUiState.Loading)
    internal val serviceWithdrawalUiState = _serviceWithdrawalUiState.asStateFlow()

    private val _logoutUiState = MutableStateFlow<LogoutUiState>(LogoutUiState.Loading)
    internal val logoutUiState = _logoutUiState.asStateFlow()

    internal fun getAdminRequestAllowList() = viewModelScope.launch {
        _swipeRefreshLoading.value = true
        _getAdminRequestAllowListUiState.value = GetAdminRequestAllowListUiState.Loading
        getAdminRequestAllowListUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getAdminRequestAllowListUiState.value = GetAdminRequestAllowListUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _getAdminRequestAllowListUiState.value = GetAdminRequestAllowListUiState.Empty
                            _swipeRefreshLoading.value = false
                        } else {
                            _getAdminRequestAllowListUiState.value = GetAdminRequestAllowListUiState.Success(result.data)
                            _swipeRefreshLoading.value = false
                        }
                    }
                    is Result.Error -> {
                        _getAdminRequestAllowListUiState.value = GetAdminRequestAllowListUiState.Error(result.exception)
                        _swipeRefreshLoading.value = false
                    }
                }
            }
    }

    internal fun allowAdminRequest(adminId: Long) = viewModelScope.launch {
        _allowAdminRequestUiState.value = AllowAdminRequestUiState.Loading
        allowAdminRequestUseCase(adminId = adminId)
            .onSuccess {
                it.catch { remoteError ->
                    _allowAdminRequestUiState.value = AllowAdminRequestUiState.Error(remoteError)
                }.collect {
                    _allowAdminRequestUiState.value = AllowAdminRequestUiState.Success
                }
            }
            .onFailure { error ->
                _allowAdminRequestUiState.value = AllowAdminRequestUiState.Error(error)
            }
    }

    internal fun rejectAdminRequest(adminId: Long) = viewModelScope.launch {
        _rejectAdminRequestUiState.value = RejectAdminRequestUiState.Loading
        rejectAdminRequestUseCase(adminId = adminId)
            .onSuccess {
                it.catch { remoteError ->
                    _rejectAdminRequestUiState.value = RejectAdminRequestUiState.Error(remoteError)
                }.collect {
                    _rejectAdminRequestUiState.value = RejectAdminRequestUiState.Success
                }
            }
            .onFailure { error ->
                _rejectAdminRequestUiState.value = RejectAdminRequestUiState.Error(error)
            }
    }

    internal fun resetAdminRequest() {
        _allowAdminRequestUiState.value = AllowAdminRequestUiState.Loading
        _rejectAdminRequestUiState.value = RejectAdminRequestUiState.Loading
    }

    internal fun serviceWithdrawal() = viewModelScope.launch {
        _serviceWithdrawalUiState.value = ServiceWithdrawalUiState.Loading
        serviceWithdrawalUseCase()
            .onSuccess {
                it.catch { remoteError ->
                    _serviceWithdrawalUiState.value = ServiceWithdrawalUiState.Error(remoteError)
                }.collect {
                    _serviceWithdrawalUiState.value = ServiceWithdrawalUiState.Success
                }
            }
            .onFailure { error ->
                _serviceWithdrawalUiState.value = ServiceWithdrawalUiState.Error(error)
            }
    }

    internal fun logout() = viewModelScope.launch {
        logoutUseCase()
            .asResult()
            .collectLatest {
                when (it) {
                    is Result.Error -> _logoutUiState.value = LogoutUiState.Error(it.exception)
                    Result.Loading -> _logoutUiState.value = LogoutUiState.Loading
                    is Result.Success -> _logoutUiState.value = LogoutUiState.Success
                }
            }
    }
}