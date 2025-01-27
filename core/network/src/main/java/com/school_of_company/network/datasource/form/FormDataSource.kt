package com.school_of_company.network.datasource.form

import com.school_of_company.network.dto.form.all.FormRequestAndResponse
import kotlinx.coroutines.flow.Flow

interface FormDataSource {
    fun createForm(expoId: String, body: FormRequestAndResponse) : Flow<Unit>
    fun modifyForm(formId: Long, body: FormRequestAndResponse) : Flow<Unit>
    fun getForm(formId: Long, formType: String) : Flow<FormRequestAndResponse>
    fun deleteForm(formId: Long) : Flow<Unit>
}