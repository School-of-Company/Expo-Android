package com.school_of_company.data.repository.form

import com.school_of_company.model.model.form.FormRequestAndResponseModel
import kotlinx.coroutines.flow.Flow

interface FormRepository {
    fun createForm(expoId: String, body: FormRequestAndResponseModel) : Flow<Unit>
    fun modifyForm(formId: Long, body: FormRequestAndResponseModel) : Flow<Unit>
    fun getForm(formId: Long, formType: String) : Flow<FormRequestAndResponseModel>
    fun deleteForm(formId: Long) : Flow<Unit>
}