package com.school_of_company.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.home.view.HomeDetailRoute
import com.school_of_company.home.view.HomeRoute

const val homeRoute = "home_route"
const val homeDetailRoute=  "home_detail_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavController.navigateToHomeDetail(navOptions: NavOptions? = null) {
    this.navigate(homeDetailRoute, navOptions)
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

fun NavGraphBuilder.homeDetailScreen(
    onBackClick: () -> Unit,
    onMessageClick: () -> Unit,
    onCheckClick: () -> Unit,
    onQrGenerateClick: () -> Unit,
    onModifyClick: () -> Unit,
    onProgramClick: () -> Unit
) {
    composable(route = homeDetailRoute) {
        HomeDetailRoute(
            onBackClick = onBackClick,
            onMessageClick = onMessageClick,
            onCheckClick = onCheckClick,
            onQrGenerateClick = onQrGenerateClick,
            onModifyClick = onModifyClick,
            onProgramClick = onProgramClick
        )
    }
}