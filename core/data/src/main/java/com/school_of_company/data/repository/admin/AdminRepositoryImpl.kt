package com.school_of_company.data.repository.admin

import com.school_of_company.model.entity.admin.AdminInformationResponseEntity
import com.school_of_company.model.entity.admin.AdminRequestAllowListResponseEntity
import com.school_of_company.network.datasource.admin.AdminDataSource
import com.school_of_company.network.mapper.admin.response.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val dataSource: AdminDataSource
) : AdminRepository {
    override fun getAdminRequestAllowList(): Flow<List<AdminRequestAllowListResponseEntity>> {
        return dataSource.getAdminRequestAllowList().transform { list ->
            emit(list.map { it.toEntity() })
        }
    }

    override fun allowAdmin(adminId: Long): Flow<Unit> {
        return dataSource.allowAdmin(adminId = adminId)
    }

    override fun rejectAdmin(adminId: Long): Flow<Unit> {
        return dataSource.rejectAdmin(adminId = adminId)
    }

    override fun serviceWithdrawal(): Flow<Unit> {
        return dataSource.serviceWithdrawal()
    }

    override fun getAdminInformation(): Flow<AdminInformationResponseEntity> {
        return dataSource.getAdminInformation().transform { response ->
            emit(response.toEntity())
        }
    }
}