package com.school_of_company.form.viewModel.viewData

import com.school_of_company.form.enum.FormType

data class DynamicFormViewData(
    val title: String,
    val formType: FormType, // SENTENCE, CHECKBOX, DROPDOWN, IMAGE, MULTIPLE
    val itemList: List<String>,
    val requiredStatus: Boolean,
    val otherJson: Boolean,
)