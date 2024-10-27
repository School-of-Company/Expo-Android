package com.school_of_company.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.signup.view.SignUpRoute

const val signUpRoute = "signUp_route"

fun NavController.navigationToSignUp(navOptions: NavOptions? = null) {
    this.navigate(signUpRoute, navOptions)
}

fun NavGraphBuilder.signUpScreen(
    onSignUpClick: () -> Unit,
    onBackClicked: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = signUpRoute) {
        SignUpRoute(
            onSignUpClick = onSignUpClick,
            onBackClick = onBackClicked,
            onErrorToast = onErrorToast
        )
    }
}