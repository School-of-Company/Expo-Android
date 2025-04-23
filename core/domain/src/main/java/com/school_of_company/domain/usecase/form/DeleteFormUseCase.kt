package com.school_of_company.domain.usecase.form

import com.school_of_company.data.repository.form.FormRepository
import javax.inject.Inject

class DeleteFormUseCase @Inject constructor(
    private val repository: FormRepository
) {
    operator fun invoke(expoId: String) = runCatching {
        repository.deleteForm(expoId = expoId)
    }
}