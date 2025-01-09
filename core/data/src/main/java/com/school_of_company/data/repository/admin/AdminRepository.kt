package com.school_of_company.data.repository.admin

import com.school_of_company.model.entity.admin.AdminRequestAllowListResponseEntity
import kotlinx.coroutines.flow.Flow

interface AdminRepository {
    fun getAdminRequestAllowList() : Flow<List<AdminRequestAllowListResponseEntity>>
    fun allowAdmin(adminId: Long) : Flow<Unit>
    fun serviceWithdrawal() : Flow<Unit>
}