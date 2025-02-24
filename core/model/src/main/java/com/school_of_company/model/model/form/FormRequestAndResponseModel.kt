package com.school_of_company.model.model.form

data class FormRequestAndResponseModel(
    val informationImage: String,
    val participantType: String, // TRAINEE, STANDARD
    val dynamicForm: List<DynamicFormModel>
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
            formType = "SENTENCE",  // 기본 폼 타입 지정 (필요에 따라 변경 가능)
            itemList = listOf(""),  // 기본 아이템 리스트
            requiredStatus = false, // 기본 필수 여부
            otherJson = false       // 기본 기타 JSON 여부
        )
    }
}