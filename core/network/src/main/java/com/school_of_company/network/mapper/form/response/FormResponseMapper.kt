package com.school_of_company.network.mapper.form.response

import com.school_of_company.model.model.form.DynamicFormModel
import com.school_of_company.model.model.form.FormRequestAndResponseModel
import com.school_of_company.network.dto.form.all.DynamicForm
import com.school_of_company.network.dto.form.all.FormRequestAndResponse
import com.school_of_company.network.util.convertJsonMapToList

fun FormRequestAndResponse.toModel(): FormRequestAndResponseModel =
    FormRequestAndResponseModel(
        informationText = this.informationText,
        participantType = this.participantType,
        dynamicForm = this.dynamicForm.map { it.toModel() }
    )

fun DynamicForm.toModel(): DynamicFormModel =
    DynamicFormModel(
        title = this.title,
        formType = this.formType,
        itemList = this.jsonData.convertJsonMapToList(),
        requiredStatus = this.requiredStatus,
        otherJson = !this.otherJson.isNullOrEmpty(),
    )