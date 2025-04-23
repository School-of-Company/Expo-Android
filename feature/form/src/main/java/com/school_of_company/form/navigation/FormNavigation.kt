package com.school_of_company.form.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.form.view.FormCreateRoute
import com.school_of_company.form.view.FormModifyRoute

const val formCreateRoute = "form_create_route"
const val formModifyRoute = "form_modify_route"

fun NavController.navigationToCreateForm(
    id: String,
    participantType: String,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$formCreateRoute/$id/$participantType",
        navOptions
    )
}

fun NavController.navigationToModifyForm(
    id: String,
    participantType: String,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$formModifyRoute/$id/$participantType",
        navOptions
    )
}

fun NavGraphBuilder.formCreateScreen(
    navigateToExpoNavigationHome: () -> Unit,
    popUpBackStack: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
) {
    composable(route = "$formCreateRoute/{id}/{participantType}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val participantType = backStackEntry.arguments?.getString("participantType") ?: ""

        FormCreateRoute(
            navigateToExpoNavigationHome = navigateToExpoNavigationHome,
            popUpBackStack = popUpBackStack,
            expoId = id,
            participantType = participantType,
            onErrorToast = onErrorToast,
        )
    }
}

fun NavGraphBuilder.formModifyScreen(
    popUpBackStack: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
) {
    composable(route = "$formModifyRoute/{id}/{participantType}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val participantType = backStackEntry.arguments?.getString("participantType") ?: ""

        FormModifyRoute(
            popUpBackStack = popUpBackStack,
            expoId = id,
            participantType = participantType,
            onErrorToast = onErrorToast,
        )
    }
}
