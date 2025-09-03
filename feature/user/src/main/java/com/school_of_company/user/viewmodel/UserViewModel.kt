package com.school_of_company.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.data.repository.admin.AdminRepository
import com.school_of_company.domain.usecase.auth.AdminLogoutUseCase
import com.school_of_company.user.viewmodel.uistate.AllowAdminRequestUiState
import com.school_of_company.user.viewmodel.uistate.GetAdminInformationUiState
import com.school_of_company.user.viewmodel.uistate.GetAdminRequestAllowListUiState
import com.school_of_company.user.viewmodel.uistate.LogoutUiState
import com.school_of_company.user.viewmodel.uistate.RejectAdminRequestUiState
import com.school_of_company.user.viewmodel.uistate.ServiceWithdrawalUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val logoutUseCase: AdminLogoutUseCase,
    private val adminRepository: AdminRepository
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

    private val _adminInformationUiState = MutableStateFlow<GetAdminInformationUiState>(GetAdminInformationUiState.Loading)
    internal val adminInformationUiState = _adminInformationUiState.asStateFlow()

    internal fun getAdminRequestAllowList() = viewModelScope.launch {
        _swipeRefreshLoading.value = true
        _getAdminRequestAllowListUiState.value = GetAdminRequestAllowListUiState.Loading
        adminRepository.getAdminRequestAllowList()
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
        adminRepository.allowAdmin(adminId = adminId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _allowAdminRequestUiState.value = AllowAdminRequestUiState.Loading
                    is Result.Success -> _allowAdminRequestUiState.value = AllowAdminRequestUiState.Success
                    is Result.Error -> _allowAdminRequestUiState.value = AllowAdminRequestUiState.Error(result.exception)
                }
            }
    }

    internal fun rejectAdminRequest(adminId: Long) = viewModelScope.launch {
        _rejectAdminRequestUiState.value = RejectAdminRequestUiState.Loading
        adminRepository.rejectAdmin(adminId = adminId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _rejectAdminRequestUiState.value = RejectAdminRequestUiState.Loading
                    is Result.Success -> _rejectAdminRequestUiState.value = RejectAdminRequestUiState.Success
                    is Result.Error -> _rejectAdminRequestUiState.value = RejectAdminRequestUiState.Error(result.exception)
                }
            }
    }

    internal fun resetAdminRequest() {
        _allowAdminRequestUiState.value = AllowAdminRequestUiState.Loading
        _rejectAdminRequestUiState.value = RejectAdminRequestUiState.Loading
    }

    internal fun serviceWithdrawal() = viewModelScope.launch {
        _serviceWithdrawalUiState.value = ServiceWithdrawalUiState.Loading
        adminRepository.serviceWithdrawal()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _serviceWithdrawalUiState.value = ServiceWithdrawalUiState.Loading
                    is Result.Success -> _serviceWithdrawalUiState.value = ServiceWithdrawalUiState.Success
                    is Result.Error -> _serviceWithdrawalUiState.value = ServiceWithdrawalUiState.Error(result.exception)
                }
            }
    }

    internal fun logout() = viewModelScope.launch {
        logoutUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _logoutUiState.value = LogoutUiState.Loading
                    is Result.Success -> _logoutUiState.value = LogoutUiState.Success
                    is Result.Error -> _logoutUiState.value = LogoutUiState.Error(result.exception)
                }
            }
    }

    internal fun adminInformation() = viewModelScope.launch {
        _adminInformationUiState.value = GetAdminInformationUiState.Loading
        adminRepository.getAdminInformation()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _adminInformationUiState.value = GetAdminInformationUiState.Loading
                    is Result.Success -> _adminInformationUiState.value = GetAdminInformationUiState.Success(result.data)
                    is Result.Error -> _adminInformationUiState.value = GetAdminInformationUiState.Error(result.exception)
                }
            }
    }
}