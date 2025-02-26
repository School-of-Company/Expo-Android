package com.school_of_company.form.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.form.view.FormCreateRoute

const val formCreateRoute = "form_create_route"
const val formModifyRoute = "form_modify_route"

fun NavController.navigationToCreateForm(
    id: String,
    informationImage: String,
    participantType: String,
    navOptions: NavOptions? = null
) {
    val encodedImage = Uri.encode(informationImage)
    this.navigate(
        route = "$formCreateRoute/$id/$encodedImage/$participantType",
        navOptions
    )
}

fun NavController.navigationToModifyForm(
    id: String,
    informationImage: String,
    participantType: String,
    navOptions: NavOptions? = null
) {
    val encodedImage = Uri.encode(informationImage)
    this.navigate(
        route = "$formModifyRoute/$id/$encodedImage/$participantType",
        navOptions
    )
}

fun NavGraphBuilder.formCreateScreen(
    popUpBackStack: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
) {
    composable(route = "$formCreateRoute/{id}/{informationImage}/{participantType}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val encodedImage = backStackEntry.arguments?.getString("informationImage") ?: ""
        val informationImage = Uri.decode(encodedImage) // URL 디코딩
        val participantType = backStackEntry.arguments?.getString("participantType") ?: ""

        FormCreateRoute(
            popUpBackStack = popUpBackStack,
            expoId = id,
            informationImage = informationImage,
            participantType = participantType,
            onErrorToast = onErrorToast,
        )
    }
}

fun NavGraphBuilder.formModifyScreen(
    popUpBackStack: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
) {
    composable(route = "$formModifyRoute/{id}/{informationImage}/{participantType}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val encodedImage = backStackEntry.arguments?.getString("informationImage") ?: ""
        val informationImage = Uri.decode(encodedImage) // URL 디코딩
        val participantType = backStackEntry.arguments?.getString("participantType") ?: ""

        FormCreateRoute(
            popUpBackStack = popUpBackStack,
            expoId = id,
            informationImage = informationImage,
            participantType = participantType,
            onErrorToast = onErrorToast,
        )
    }
}
