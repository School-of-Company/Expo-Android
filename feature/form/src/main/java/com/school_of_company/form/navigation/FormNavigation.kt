package com.school_of_company.form.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.form.view.FormCreateRoute

const val formCreateRoute = "form_create_route"

fun NavController.navigationToFormCreate(
    id: String,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$formCreateRoute/${id}",
        navOptions
    )
}

fun NavGraphBuilder.formCreateScreen(
    popUpBackStack: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
) {
    composable(route = "$formCreateRoute/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        FormCreateRoute(
            expoId = id,
            popUpBackStack = popUpBackStack
        )
    }
}