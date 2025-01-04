package com.school_of_company.expo_android.navigation

import com.school_of_company.design_system.R

// TopLevelDestination
enum class TopLevelDestination(
    val unSelectedIcon: Int,
    val iconText: String
) {
    HOME(
        unSelectedIcon = R.drawable.ic_house,
        iconText = "홈"
    ),

    EXPO(
        unSelectedIcon = R.drawable.ic_plus,
        iconText = "박람회 생성"
    ),

    EXPO_CREATED(
        unSelectedIcon = R.drawable.ic_expo,
        iconText = "등록된 박람회"
    ),

    PROFILE(
        unSelectedIcon = R.drawable.ic_user,
        iconText = "프로필"
    )
}