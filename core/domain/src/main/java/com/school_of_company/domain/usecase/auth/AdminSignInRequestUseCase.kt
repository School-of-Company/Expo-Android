package com.school_of_company.domain.usecase.auth

import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.model.param.auth.AdminSignInRequestParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AdminSignInRequestUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(body: AdminSignInRequestParam): Flow<Unit> = flow {
        authRepository.adminSignIn(body)
            .onEach { tokenResponse ->
                authRepository.saveToken(tokenResponse)
                emit(Unit)
            }
    }
}