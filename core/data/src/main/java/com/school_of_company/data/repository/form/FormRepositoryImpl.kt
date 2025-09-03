package com.school_of_company.data.repository.form

import com.school_of_company.model.model.form.FormRequestAndResponseModel
import com.school_of_company.network.datasource.form.FormDataSource
import com.school_of_company.network.mapper.form.request.toModel
import com.school_of_company.network.mapper.form.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class FormRepositoryImpl @Inject constructor(
    private val dataSource: FormDataSource
) : FormRepository {
    override fun createForm(expoId: String, body: FormRequestAndResponseModel): Flow<Unit> {
        return dataSource.createForm(
            expoId = expoId,
            body = body.toModel()
        )
    }

    override fun modifyForm(expoId: String, body: FormRequestAndResponseModel): Flow<Unit> {
        return dataSource.modifyForm(
            expoId = expoId,
            body = body.toModel()
        )
    }

    override fun getForm(expoId: String, participantType: String): Flow<FormRequestAndResponseModel> {
        return dataSource.getForm(
            expoId = expoId,
            participantType = participantType
        ).transform { response ->
            emit(response.toModel())
        }
    }

    override fun deleteForm(expoId: String): Flow<Unit> {
        return dataSource.deleteForm(expoId = expoId)
    }
}