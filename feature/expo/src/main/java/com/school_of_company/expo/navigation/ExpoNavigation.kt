package com.school_of_company.expo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.expo.view.ExpoAddressSearchRoute
import com.school_of_company.expo.view.ExpoCreateRoute
import com.school_of_company.expo.view.ExpoCreatedRoute
import com.school_of_company.expo.view.ExpoDetailRoute
import com.school_of_company.expo.view.ExpoModifyRoute
import com.school_of_company.expo.view.ExpoNavigationHomeRoute
import com.school_of_company.expo.view.ExpoRoute

const val homeRoute = "home_route"
const val expoDetailRoute = "expo_detail_route"
const val expoModifyRoute = "expo_modify_route"
const val expoCreateRoute = "expo_create_route"
const val expoCreatedRoute = "expo_created_route"
const val expoAddressSearchRoute = "expo_address_search_route"
const val expoNavigationHomeRoute = "expo_navigation_home_route"

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

fun NavController.navigateToExpoCreated(navOptions: NavOptions? = null) {
    this.navigate(expoCreatedRoute, navOptions)
}

fun NavController.navigateToExpoAddressSearch(navOptions: NavOptions? = null) {
    this.navigate(expoAddressSearchRoute, navOptions)
}

fun NavController.navigateToExpoNavigationHome(navOptions: NavOptions? = null) {
    this.navigate(expoNavigationHomeRoute, navOptions)
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
    onCheckClick: (String, String, String) -> Unit,
    onModifyClick: (String) -> Unit,
    onProgramClick: (String) -> Unit,
    onMessageClick: (String, String) -> Unit,
    navigationToFormCreate: (String, String) -> Unit,
    navigationToFormModify: (String, String) -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
) {
    composable(route = "$expoDetailRoute/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        ExpoDetailRoute(
            id = id,
            onBackClick = onBackClick,
            onCheckClick = onCheckClick,
            onModifyClick = onModifyClick,
            onProgramClick = onProgramClick,
            onMessageClick = onMessageClick,
            navigationToFormCreate = navigationToFormCreate,
            navigationToFormModify = navigationToFormModify,
            onErrorToast = onErrorToast,
        )
    }
}

fun NavGraphBuilder.expoModifyScreen(
    onBackClick: () -> Unit,
    navigateToExpoAddressSearch: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = "$expoModifyRoute/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        ExpoModifyRoute(
            id = id,
            navigateToExpoAddressSearch = navigateToExpoAddressSearch,
            onBackClick = onBackClick,
            onErrorToast = onErrorToast
        )
    }
}

fun NavGraphBuilder.expoCreateScreen(
    navigateToExpoAddressSearch: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
) {
    composable(route = expoCreateRoute) {
        ExpoCreateRoute(
            navigateToExpoAddressSearch = navigateToExpoAddressSearch,
            onErrorToast = onErrorToast
        )
    }
}

fun NavGraphBuilder.expoCreatedScreen(
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = expoCreatedRoute) {
        ExpoCreatedRoute(onErrorToast = onErrorToast)
    }
}

fun NavGraphBuilder.expoAddressSearchScreen(
    popUpBackStack: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = expoAddressSearchRoute) {
        ExpoAddressSearchRoute(
            popUpBackStack = popUpBackStack,
            onErrorToast = onErrorToast,
        )
    }
}

fun NavGraphBuilder.expoNavigationHomeScreen(
    navigationToHome: () -> Unit
) {
    composable(route = expoNavigationHomeRoute) {
        ExpoNavigationHomeRoute(
            navigateToHome = navigationToHome
        )
    }
}