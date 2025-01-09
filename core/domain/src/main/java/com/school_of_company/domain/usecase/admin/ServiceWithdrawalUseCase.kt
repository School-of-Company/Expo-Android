package com.school_of_company.domain.usecase.admin

import com.school_of_company.data.repository.admin.AdminRepository
import com.school_of_company.model.entity.admin.AdminRequestAllowListResponseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ServiceWithdrawalUseCase @Inject constructor(
    private val repository: AdminRepository
) {
    operator fun invoke() = runCatching {
        repository.serviceWithdrawal()
    }
}