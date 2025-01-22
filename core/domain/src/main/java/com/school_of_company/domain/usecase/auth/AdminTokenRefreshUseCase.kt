package com.school_of_company.domain.usecase.auth

import com.school_of_company.data.repository.auth.AuthRepository
import javax.inject.Inject

class AdminTokenRefreshUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = runCatching {
        authRepository.adminTokenRefresh().collect { token ->
            authRepository.saveToken(token)
        }
    }
}