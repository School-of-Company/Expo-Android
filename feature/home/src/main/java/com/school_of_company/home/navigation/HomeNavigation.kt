package com.school_of_company.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.home.view.HomeRoute

const val homeRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigationToDetail: () -> Unit
) {
    composable(route = homeRoute) {
        HomeRoute(
            navigationToDetail = navigationToDetail
        )
    }
}