package com.school_of_company.expo_android.navigation

import com.school_of_company.design_system.R
import com.school_of_company.expo.navigation.expoCreateRoute
import com.school_of_company.expo.navigation.expoCreatedRoute
import com.school_of_company.expo.navigation.homeRoute
import com.school_of_company.user.navigation.profileRoute
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

// TopLevelDestination
enum class TopLevelDestination(
    val unSelectedIcon: Int,
    val iconText: String,
    val routeName: String,
) {
    HOME(
        unSelectedIcon = R.drawable.ic_house,
        iconText = "홈",
        routeName = homeRoute,
    ),

    EXPO(
        unSelectedIcon = R.drawable.ic_plus,
        iconText = "박람회 생성",
        routeName = expoCreateRoute,
    ),

    EXPO_CREATED(
        unSelectedIcon = R.drawable.ic_expo,
        iconText = "등록된 박람회",
        routeName = expoCreatedRoute,
    ),

    PROFILE(
        unSelectedIcon = R.drawable.ic_user,
        iconText = "프로필",
        routeName = profileRoute,
    );

    companion object {
        val topLevelDestinations: ImmutableList<TopLevelDestination> = persistentListOf(
            HOME, EXPO, EXPO_CREATED, PROFILE
        )
    }
}