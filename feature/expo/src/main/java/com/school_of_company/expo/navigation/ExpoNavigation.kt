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

fun NavController.navigateToExpoDetail(
    id: String,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$expoDetailRoute/${id}",
        navOptions
    )
}

fun NavController.navigateToExpoModify(
    id: String,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$expoModifyRoute/${id}",
        navOptions
    )
}

fun NavController.navigateToExpoCreate(navOptions: NavOptions? = null) {
    this.navigate(expoCreateRoute, navOptions)
}

fun NavGraphBuilder.expoScreen(
    navigationToDetail: (String) -> Unit
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
    onModifyClick: (String) -> Unit,
    onProgramClick: (String) -> Unit
) {
    composable(route = "$expoDetailRoute/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        ExpoDetailRoute(
            id = id,
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
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = "$expoModifyRoute/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        ExpoModifyRoute(
            id = id,
            onBackClick = onBackClick,
            onErrorToast = onErrorToast
        )
    }
}

fun NavGraphBuilder.expoCreateScreen(
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = expoCreateRoute) {
        ExpoCreateRoute(
            onErrorToast = onErrorToast
        )
    }
}