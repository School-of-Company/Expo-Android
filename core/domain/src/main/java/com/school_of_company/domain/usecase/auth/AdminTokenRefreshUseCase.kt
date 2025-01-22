package com.school_of_company.domain.usecase.auth

import com.school_of_company.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AdminTokenRefreshUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Unit> = flow {
        authRepository.adminTokenRefresh()
            .onEach { token ->
                authRepository.saveToken(token)
                emit(Unit)
            }
    }
}