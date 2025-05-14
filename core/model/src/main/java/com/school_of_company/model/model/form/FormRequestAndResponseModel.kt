package com.school_of_company.model.model.form

import androidx.compose.runtime.Immutable

data class FormRequestAndResponseModel(
    val informationText: String,
    val participantType: String, // TRAINEE, STANDARD
    val dynamicForm: List<DynamicFormModel>,
)


@Immutable
data class DynamicFormModel(
    val title: String,
    val formType: String, // SENTENCE, CHECKBOX, DROPDOWN, IMAGE, MULTIPLE
    val itemList: List<String>,
    val requiredStatus: Boolean,
    val otherJson: Boolean,
) {
    companion object {
        fun createDefault(): DynamicFormModel = DynamicFormModel(
            title = "",
            formType = "SENTENCE",
            itemList = listOf(""),
            requiredStatus = false,
            otherJson = false,
        )
    }
}