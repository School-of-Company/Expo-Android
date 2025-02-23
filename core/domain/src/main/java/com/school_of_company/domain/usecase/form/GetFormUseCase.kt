package com.school_of_company.domain.usecase.form

import com.school_of_company.data.repository.form.FormRepository
import com.school_of_company.model.model.form.FormRequestAndResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFormUseCase @Inject constructor(
    private val repository: FormRepository
) {
    operator fun invoke(formId: String, formType: String): Flow<FormRequestAndResponseModel> =
        repository.getForm(
            formId = formId,
            formType = formType
        )

}