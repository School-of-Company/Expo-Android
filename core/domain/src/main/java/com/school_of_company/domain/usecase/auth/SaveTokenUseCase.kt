package com.school_of_company.domain.usecase.auth

import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.model.model.auth.AdminTokenResponseModel
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(token: AdminTokenResponseModel) = runCatching {
        authRepository.saveToken(token = token)
    }
}