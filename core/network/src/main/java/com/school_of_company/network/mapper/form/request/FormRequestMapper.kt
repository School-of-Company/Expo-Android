package com.school_of_company.network.mapper.form.request

import com.school_of_company.model.model.form.DynamicFormModel
import com.school_of_company.model.model.form.FormRequestAndResponseModel
import com.school_of_company.network.dto.form.all.DynamicForm
import com.school_of_company.network.dto.form.all.FormRequestAndResponse
import com.school_of_company.network.util.convertListToJsonMap

fun FormRequestAndResponseModel.toModel(): FormRequestAndResponse =
    FormRequestAndResponse(
        informationImage = this.informationImage,
        participantType = this.participantType,
        dynamicForm = this.dynamicForm.map { it.toModel() }
    )

fun DynamicFormModel.toModel(): DynamicForm =
    DynamicForm(
        title = this.title,
        formType = this.formType,
        jsonData = this.jsonData.convertListToJsonMap(),
        requiredStatus = this.requiredStatus,
        otherJson = this.otherJson,
    )