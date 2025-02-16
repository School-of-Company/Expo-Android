package com.school_of_company.network.dto.form.all

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FormRequestAndResponse(
    @Json(name = "informationImage") val informationImage: String,
    @Json(name = "participantType") val participantType: String, // TRAINEE, STANDARD
    @Json(name = "dynamicForm") val dynamicForm: List<DynamicForm>
)

@JsonClass(generateAdapter = true)
data class DynamicForm(
    @Json(name = "title") val title: String,
    @Json(name = "formType") val formType: String, // SENTENCE, CHECKBOX, DROPDOWN, IMAGE, MULTIPLE
    @Json(name = "jsonData") val jsonData: String,
    @Json(name = "requiredStatus") val requiredStatus: String,
    @Json(name = "otherJson") val otherJson: String,
)
