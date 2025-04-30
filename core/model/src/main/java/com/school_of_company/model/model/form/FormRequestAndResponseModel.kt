package com.school_of_company.model.model.form

data class FormRequestAndResponseModel(
    val informationText: String,
    val participantType: String, // TRAINEE, STANDARD
    val dynamicForm: List<DynamicFormModel>,
)

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