package com.school_of_company.network.datasource.form

import com.school_of_company.network.api.FormAPI
import com.school_of_company.network.dto.form.all.FormRequestAndResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FormDataSourceImpl @Inject constructor(
    private val service: FormAPI
) : FormDataSource {
    override fun createForm(expoId: String, body: FormRequestAndResponse): Flow<Unit> =
        performApiRequest { service.createForm(
            expoId = expoId,
            body = body
        ) }

    override fun modifyForm(expoId: String, body: FormRequestAndResponse): Flow<Unit> =
        performApiRequest { service.modifyForm(
            formId = expoId,
            body = body
        ) }

    override fun getForm(expoId: String, formType: String): Flow<FormRequestAndResponse> =
        performApiRequest { service.getForm(
            formId = expoId,
            formType = formType
        ) }

    override fun deleteForm(expoId: String): Flow<Unit> =
        performApiRequest { service.deleteForm(formId = expoId) }
}