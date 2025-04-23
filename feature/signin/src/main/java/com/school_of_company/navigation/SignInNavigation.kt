package com.school_of_company.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.view.SignInRoute

const val sigInRoute = "signIn_route"

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    this.navigate(sigInRoute, navOptions)
}

fun NavGraphBuilder.signInScreen(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
) {
    composable(route = sigInRoute) {
        SignInRoute(
            onSignInClick = onSignInClick,
            onSignUpClick = onSignUpClick,
            onErrorToast = onErrorToast
        )
    }
}