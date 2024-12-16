package com.school_of_company.standard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.standard.view.StandardProgramParticipantRoute

const val standardProgramParticipantRoute = "standard_program_participant_route"

fun NavController.navigateToStandardProgramParticipant(
    id: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$standardProgramParticipantRoute/${id}",
        navOptions
    )
}

fun NavGraphBuilder.standardProgramParticipantScreen(
    onBackClick: () -> Unit,
    navigateToQrScanner: (Long, Long) -> Unit
) {
    composable(route = "$standardProgramParticipantRoute/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
        if (id != null) {
            StandardProgramParticipantRoute(
                id = id,
                onBackClick = onBackClick,
                navigateToQrScanner = navigateToQrScanner
            )
        }
    }
}