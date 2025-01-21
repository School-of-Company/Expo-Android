package com.school_of_company.user.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.user.view.UserRoute

const val profileRoute = "profile_route"

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    this.navigate(profileRoute, navOptions)
}

fun NavGraphBuilder.profileScreen(
    onMainNavigate: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
) {
    composable(route = profileRoute) {
        UserRoute(
            onMainNavigate = onMainNavigate,
            onErrorToast = onErrorToast,
        )
    }
}