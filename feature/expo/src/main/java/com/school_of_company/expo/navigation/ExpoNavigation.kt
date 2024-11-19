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
    id: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$expoDetailRoute/${id}",
        navOptions
    )
}

fun NavController.navigateToExpoModify(
    id: Long,
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
    navigationToDetail: (Long) -> Unit
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
    onModifyClick: (Long) -> Unit,
    onProgramClick: () -> Unit
) {
    composable(route = "$expoDetailRoute/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
        if (id != null) {
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
}

fun NavGraphBuilder.expoModifyScreen(
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = "$expoModifyRoute/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
        if (id != null) {
            ExpoModifyRoute(
                id = id,
                onBackClick = onBackClick,
                onErrorToast = onErrorToast
            )
        }
    }
}

fun NavGraphBuilder.expoCreateScreen(
    onExpoCreateClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = expoCreateRoute) {
        ExpoCreateRoute(
            onExpoCreateClick = onExpoCreateClick,
            onErrorToast = onErrorToast
        )
    }
}