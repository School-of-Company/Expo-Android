package com.school_of_company.user.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.user.view.UserRoute

const val userRoute = "user_route"

fun NavController.navigateToUser(navOptions: NavOptions? = null) {
    this.navigate(userRoute, navOptions)
}

fun NavGraphBuilder.userScreen() {
    composable(route = userRoute) {
        UserRoute()
    }
}