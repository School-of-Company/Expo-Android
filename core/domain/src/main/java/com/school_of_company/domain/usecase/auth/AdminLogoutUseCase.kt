package com.school_of_company.domain.usecase.auth

import com.school_of_company.data.repository.auth.AuthRepository
import javax.inject.Inject

class AdminLogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = runCatching {
        authRepository.adminLogout()
    }
}