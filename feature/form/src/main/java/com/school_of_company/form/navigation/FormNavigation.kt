package com.school_of_company.form.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.form.view.FormRoute
import android.net.Uri
import com.school_of_company.form.enum.FormActionType

const val formCreateRoute = "form_create_route"

fun NavController.navigationToForm(
    id: String,
    informationImage: String,
    participantType: String,
    formActionType: FormActionType = FormActionType.CREATE,
    navOptions: NavOptions? = null
) {
    val encodedImage = Uri.encode(informationImage)
    this.navigate(
        route = "$formCreateRoute/$id/$encodedImage/$participantType/$formActionType",
        navOptions
    )
}

fun NavGraphBuilder.formScreen(
    popUpBackStack: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
) {
    composable(route = "$formCreateRoute/{id}/{informationImage}/{participantType}/{formActionType}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val encodedImage = backStackEntry.arguments?.getString("informationImage") ?: ""
        val informationImage = Uri.decode(encodedImage) // URL 디코딩
        val participantType = backStackEntry.arguments?.getString("participantType") ?: ""
        val formActionType = backStackEntry.arguments?.getString("formActionType") ?: FormActionType.CREATE.name

        FormRoute(
            popUpBackStack = popUpBackStack,
            expoId = id,
            informationImage = informationImage,
            participantType = participantType,
            formActionType = FormActionType.valueOf(formActionType),
            onErrorToast = onErrorToast,
        )
    }
}
