package com.school_of_company.form.enum

import com.school_of_company.design_system.R

enum class FromType(val iconRes: Int, val typeName: String) {
    SENTENCE(iconRes = R.drawable.ic_long_content, typeName = "문장형"),
    CHECKBOX(iconRes = R.drawable.ic_check_box, typeName = "체크박스"),
    DROPDOWN(iconRes = R.drawable.ic_drop_down, typeName = "드롭다운"),
    IMAGE(iconRes = R.drawable.ic_image, typeName = "이미지"),
    MULTIPLE(iconRes = R.drawable.ic_two_circle, typeName = "객관식 질문"),
}