package com.school_of_company.form.viewModel.util

import com.school_of_company.form.viewModel.viewData.DynamicFormViewData
import com.school_of_company.model.model.form.DynamicFormModel

internal fun DynamicFormViewData.toModel() = DynamicFormModel(
    title = title,
    formType = formType.typeName,
    jsonData = itemList,
    requiredStatus = requiredStatus,
    otherJson = if (otherJson) "etc" else null,
)