package com.school_of_company.training.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.training.view.TrainingProgramParticipantRoute

const val trainingProgramParticipantRoute = "training_program_participant_route"

fun NavController.navigateToTrainingProgramParticipant(
    id: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$trainingProgramParticipantRoute/${id}",
        navOptions
    )
}

fun NavGraphBuilder.trainingProgramParticipantScreen(
    onBackClick: () -> Unit,
    navigateToQrScanner: (Long, Long) -> Unit
) {
    composable(route = "$trainingProgramParticipantRoute/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
        if (id != null) {
            TrainingProgramParticipantRoute(
                id = id,
                onBackClick = onBackClick,
                navigateToQrScanner = navigateToQrScanner
            )
        }
    }
}