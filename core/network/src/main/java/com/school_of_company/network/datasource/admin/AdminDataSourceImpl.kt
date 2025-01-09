package com.school_of_company.network.datasource.admin

import com.school_of_company.network.api.AdminAPI
import com.school_of_company.network.dto.admin.response.AdminRequestAllowListResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AdminDataSourceImpl @Inject constructor(
    private val service: AdminAPI
) : AdminDataSource {
    override fun getAdminRequestAllowList(): Flow<List<AdminRequestAllowListResponse>> =
        performApiRequest { service.getAdminRequestAllowList() }

    override fun allowAdmin(adminId: Long): Flow<Unit> =
        performApiRequest { service.allowAdmin(adminId = adminId) }

    override fun serviceWithdrawal(): Flow<Unit> =
        performApiRequest { service.serviceWithdrawal() }
}