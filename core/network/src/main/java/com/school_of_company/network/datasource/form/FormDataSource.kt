package com.school_of_company.network.datasource.form

import com.school_of_company.network.dto.form.all.FormRequestAndResponse
import kotlinx.coroutines.flow.Flow

interface FormDataSource {
    fun createForm(expoId: String, body: FormRequestAndResponse) : Flow<Unit>
    fun modifyForm(expoId: String, body: FormRequestAndResponse) : Flow<Unit>
    fun getForm(expoId: String, participantType: String) : Flow<FormRequestAndResponse>
    fun deleteForm(expoId: String) : Flow<Unit>
}