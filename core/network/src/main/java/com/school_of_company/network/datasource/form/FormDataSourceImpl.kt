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
            expoId = expoId,
            body = body
        ) }

    override fun getForm(expoId: String, participantType: String): Flow<FormRequestAndResponse> =
        performApiRequest { service.getForm(
            expoId = expoId,
            participantType = participantType
        ) }

    override fun deleteForm(expoId: String): Flow<Unit> =
        performApiRequest { service.deleteForm(expoId = expoId) }
}