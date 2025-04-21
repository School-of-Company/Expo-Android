package com.school_of_company.program.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.program.view.ProgramDetailParticipantManagementRoute
import com.school_of_company.program.view.ProgramDetailProgramRoute
import com.school_of_company.program.view.QrScannerRoute

const val programDetailProgramRoute = "program_detail_program_route"
const val programDetailParticipantManagementRoute = "program_detail_participant_management_route"
const val qrScannerRoute = "qr_scanner_route"

fun NavController.navigateToProgramDetailProgram(
    id: String,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$programDetailProgramRoute/${id}",
        navOptions
    )
}

fun NavController.navigateToProgramDetailParticipantManagement(
    id: String,
    startDate: String,
    endDate: String,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$programDetailParticipantManagementRoute/${id}/${startDate}/${endDate}",
        navOptions
    )
}

fun NavController.navigateQrScanner(
    id: Long,
    screenType: String,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$qrScannerRoute/$id/$screenType",
        navOptions
    )
}

fun NavGraphBuilder.programDetailProgramScreen(
    onBackClick: () -> Unit,
    navigateToTrainingProgramDetail: (Long) -> Unit,
    navigateToStandardProgramDetail: (Long) -> Unit
) {
    composable(route = "$programDetailProgramRoute/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        ProgramDetailProgramRoute(
            id = id,
            onBackClick = onBackClick,
            navigateToTrainingProgramDetail = navigateToTrainingProgramDetail,
            navigateToStandardProgramDetail = navigateToStandardProgramDetail
        )
    }
}

fun NavGraphBuilder.programDetailParticipantManagementScreen(onBackClick: () -> Unit) {
    composable(route = "$programDetailParticipantManagementRoute/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val startDate = backStackEntry.arguments?.getString("startDate") ?: ""
        val endDate = backStackEntry.arguments?.getString("endDate") ?: ""
        ProgramDetailParticipantManagementRoute(
            id = id,
            startDate = startDate,
            endDate = endDate,
            onBackClick = onBackClick
        )
    }
}

fun NavGraphBuilder.qrScannerScreen(
    onBackClick: () -> Unit,
    onPermissionBlock: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = "$qrScannerRoute/{id}/{screenType}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
        val screenType = backStackEntry.arguments?.getString("screenType")

        if (id != null && screenType != null) {
            QrScannerRoute(
                id = id,
                screenType = screenType,
                onBackClick = onBackClick,
                onPermissionBlock = onPermissionBlock,
                onErrorToast = onErrorToast
            )
        }
    }
}
