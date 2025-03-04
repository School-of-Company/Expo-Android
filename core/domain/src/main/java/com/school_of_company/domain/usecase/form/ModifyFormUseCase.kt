package com.school_of_company.domain.usecase.form

import com.school_of_company.data.repository.form.FormRepository
import com.school_of_company.model.model.form.FormRequestAndResponseModel
import javax.inject.Inject

class ModifyFormUseCase @Inject constructor(
    private val repository: FormRepository
) {
    operator fun invoke(expoId: String, body: FormRequestAndResponseModel) = runCatching {
        repository.modifyForm(
            expoId = expoId,
            body = body
        )
    }
}