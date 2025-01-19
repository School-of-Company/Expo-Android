package com.school_of_company.domain.usecase.admin

import com.school_of_company.data.repository.admin.AdminRepository
import javax.inject.Inject

class RejectAdminRequestUseCase @Inject constructor(
    private val repository: AdminRepository
) {
    operator fun invoke(adminId: Long) = runCatching {
        repository.rejectAdmin(adminId = adminId)
    }
}