package com.school_of_company.network.datasource.form

import com.school_of_company.network.dto.form.all.FormRequestAndResponse
import kotlinx.coroutines.flow.Flow

interface FormDataSource {
    fun createForm(expoId: String, body: FormRequestAndResponse) : Flow<Unit>
    fun modifyForm(formId: String, body: FormRequestAndResponse) : Flow<Unit>
    fun getForm(formId: String, formType: String) : Flow<FormRequestAndResponse>
    fun deleteForm(formId: String) : Flow<Unit>
}