package com.school_of_company.domain.usecase.auth

import com.school_of_company.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AdminTokenRefreshUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Unit> =
        authRepository.adminTokenRefresh()
            .onEach { tokenResponse ->
                authRepository.saveToken(tokenResponse)
            }.map { Unit }
}