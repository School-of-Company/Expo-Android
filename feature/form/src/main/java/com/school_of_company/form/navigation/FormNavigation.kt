package com.school_of_company.form.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.form.view.FormCreateRoute
import android.net.Uri

const val formCreateRoute = "form_create_route"

fun NavController.navigationToFormCreate(
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
