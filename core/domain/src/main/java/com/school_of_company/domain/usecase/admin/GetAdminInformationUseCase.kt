package com.school_of_company.domain.usecase.admin

import com.school_of_company.data.repository.admin.AdminRepository
import com.school_of_company.model.entity.admin.AdminInformationResponseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAdminInformationUseCase @Inject constructor(
    private val repository: AdminRepository
) {
    operator fun invoke(): Flow<AdminInformationResponseEntity> =
        repository.getAdminInformation()
}