package com.school_of_company.data.repository.form

import com.school_of_company.model.model.form.FormRequestAndResponseModel
import kotlinx.coroutines.flow.Flow

interface FormRepository {
    fun createForm(expoId: String, body: FormRequestAndResponseModel) : Flow<Unit>
    fun modifyForm(formId: String, body: FormRequestAndResponseModel) : Flow<Unit>
    fun getForm(formId: String, formType: String) : Flow<FormRequestAndResponseModel>
    fun deleteForm(formId: String) : Flow<Unit>
}