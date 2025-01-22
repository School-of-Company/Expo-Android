package com.school_of_company.domain.usecase.auth

import com.school_of_company.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdminTokenRefreshUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Unit> = flow {
        emit(
            authRepository.adminTokenRefresh().collect { token ->
                authRepository.saveToken(token)
            }
        )
    }
}