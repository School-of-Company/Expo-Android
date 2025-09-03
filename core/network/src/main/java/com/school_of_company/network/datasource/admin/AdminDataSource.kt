package com.school_of_company.network.datasource.admin

import com.school_of_company.network.dto.admin.response.AdminInformationResponse
import com.school_of_company.network.dto.admin.response.AdminRequestAllowListResponse
import kotlinx.coroutines.flow.Flow

interface AdminDataSource {
    fun getAdminRequestAllowList(): Flow<List<AdminRequestAllowListResponse>>
    fun allowAdmin(adminId: Long): Flow<Unit>
    fun rejectAdmin(adminId: Long): Flow<Unit>
    fun serviceWithdrawal(): Flow<Unit>
    fun getAdminInformation(): Flow<AdminInformationResponse>
}