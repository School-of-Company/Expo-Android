package com.school_of_company.data.repository.form

import com.school_of_company.model.model.form.FormRequestAndResponseModel
import kotlinx.coroutines.flow.Flow

interface FormRepository {
    fun createForm(expoId: String, body: FormRequestAndResponseModel) : Flow<Unit>
    fun modifyForm(expoId: String, body: FormRequestAndResponseModel) : Flow<Unit>
    fun getForm(expoId: String, participantType: String) : Flow<FormRequestAndResponseModel>
    fun deleteForm(expoId: String) : Flow<Unit>
}