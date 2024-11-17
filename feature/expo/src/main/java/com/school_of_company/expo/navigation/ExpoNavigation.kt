package com.school_of_company.expo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.expo.view.ExpoCreateRoute
import com.school_of_company.expo.view.ExpoDetailRoute
import com.school_of_company.expo.view.ExpoModifyRoute
import com.school_of_company.expo.view.ExpoRoute

const val homeRoute = "home_route"
const val expoDetailRoute=  "expo_detail_route"
const val expoModifyRoute = "expo_modify_route"
const val expoCreateRoute = "expo_create_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavController.navigateToExpoDetail(navOptions: NavOptions? = null) {
    this.navigate(expoDetailRoute, navOptions)
}

fun NavController.navigateToExpoModify(navOptions: NavOptions? = null) {
    this.navigate(expoModifyRoute, navOptions)
}

fun NavController.navigateToExpoCreate(navOptions: NavOptions? = null) {
    this.navigate(expoCreateRoute, navOptions)
}

fun NavGraphBuilder.expoScreen(
    navigationToDetail: () -> Unit
) {
    composable(route = homeRoute) {
        ExpoRoute(
            navigationToDetail = navigationToDetail
        )
    }
}

fun NavGraphBuilder.expoDetailScreen(
    onBackClick: () -> Unit,
    onMessageClick: () -> Unit,
    onCheckClick: () -> Unit,
    onQrGenerateClick: () -> Unit,
    onModifyClick: () -> Unit,
    onProgramClick: () -> Unit
) {
    composable(route = expoDetailRoute) {
        ExpoDetailRoute(
            onBackClick = onBackClick,
            onMessageClick = onMessageClick,
            onCheckClick = onCheckClick,
            onQrGenerateClick = onQrGenerateClick,
            onModifyClick = onModifyClick,
            onProgramClick = onProgramClick
        )
    }
}

fun NavGraphBuilder.expoModifyScreen(
    onBackClick: () -> Unit,
    onModifyClick: () -> Unit
) {
    composable(route = expoModifyRoute) {
        ExpoModifyRoute(
            onBackClick = onBackClick,
            onModifyClick = onModifyClick
        )
    }
}

fun NavGraphBuilder.expoCreateScreen(
    onExpoCreateClick: () -> Unit
) {
    composable(route = expoCreateRoute) {
        ExpoCreateRoute(
            onExpoCreateClick = onExpoCreateClick
        )
    }
}