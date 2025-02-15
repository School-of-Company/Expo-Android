package com.school_of_company.form.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.form.view.FormCreateRoute

const val formCreateRoute = "form_create_route"

fun NavController.navigationToFormCreate(navOptions: NavOptions? = null) {
    this.navigate(formCreateRoute, navOptions)
}

fun NavGraphBuilder.formCreateScreen(
    popUpBackStack: () -> Unit,
) {
    composable(formCreateRoute) {
        FormCreateRoute(
            popUpBackStack = popUpBackStack,
        )
    }
}