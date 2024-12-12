package com.school_of_company.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.home.view.HomeDetailParticipantManagementRoute
import com.school_of_company.home.view.HomeDetailProgramParticipantRoute
import com.school_of_company.home.view.HomeDetailProgramRoute
import com.school_of_company.home.view.HomeDetailStandardProgramParticipantRoute
import com.school_of_company.home.view.HomeDetailStandardProgramParticipantScreen
import com.school_of_company.home.view.QrScannerRoute
import com.school_of_company.home.view.SendMessageRoute

const val homeSendMessageRoute = "home_send_message_route"
const val homeDetailProgramRoute = "home_detail_program_route"
const val homeDetailTrainingProgramParticipantRoute = "home_detail_program_participant_route"
const val homeDetailStandardProgramParticipantRoute = "home_detail_standard_program_participant_route"
const val homeDetailParticipantManagementRoute = "home_detail_participant_management_route"
const val qrScannerRoute = "qr_scanner_route"

fun NavController.navigateToHomeSendMessage(navOptions: NavOptions? = null) {
    this.navigate(homeSendMessageRoute, navOptions)
}

fun NavController.navigateToHomeDetailProgram(
    id: String,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$homeDetailProgramRoute/${id}",
        navOptions
    )
}

fun NavController.navigateToHomeDetailTrainingProgramParticipant(
    id: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$homeDetailTrainingProgramParticipantRoute/${id}",
        navOptions
    )
}

fun NavController.navigateToHomeDetailStandardProgramParticipant(
    id: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$homeDetailStandardProgramParticipantRoute/${id}",
        navOptions
    )
}

fun NavController.navigateToHomeDetailParticipantManagement(navOptions: NavOptions? = null) {
    this.navigate(homeDetailParticipantManagementRoute, navOptions)
}

fun NavGraphBuilder.homeSendMessageScreen(
    onBackClick: () -> Unit
) {
    composable(route = homeSendMessageRoute) {
        SendMessageRoute(
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateQrScanner(
    id: Long,
    traineeId: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$qrScannerRoute/${id}/${traineeId}",
        navOptions
    )
}

fun NavGraphBuilder.homeDetailProgramScreen(
    onBackClick: () -> Unit,
    navigateToTrainingProgramDetail: (Long) -> Unit,
    navigateToStandardProgramDetail: (Long) -> Unit
) {
    composable(route = "$homeDetailProgramRoute/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        HomeDetailProgramRoute(
            id = id,
            onBackClick = onBackClick,
            navigateToTrainingProgramDetail = navigateToTrainingProgramDetail,
            navigateToStandardProgramDetail = navigateToStandardProgramDetail
        )
    }
}

fun NavGraphBuilder.homeDetailTrainingProgramParticipantScreen(
    onBackClick: () -> Unit,
    navigateToQrScanner: (Long, Long) -> Unit
) {
    composable(route = "$homeDetailTrainingProgramParticipantRoute/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
        if (id != null) {
            HomeDetailProgramParticipantRoute(
                id = id,
                onBackClick = onBackClick,
                navigateToQrScanner = navigateToQrScanner
            )
        }
    }
}

fun NavGraphBuilder.homeDetailStandardProgramParticipantScreen(
    onBackClick: () -> Unit,
    navigateToQrScanner: (Long, Long) -> Unit
) {
    composable(route = "$homeDetailStandardProgramParticipantRoute/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
        if (id != null) {
            HomeDetailStandardProgramParticipantRoute(
                id = id,
                onBackClick = onBackClick,
                navigateToQrScanner = navigateToQrScanner
            )
        }
    }
}

fun NavGraphBuilder.homeDetailParticipantManagementScreen(
    onBackClick: () -> Unit
) {
    composable(route = homeDetailParticipantManagementRoute) {
        HomeDetailParticipantManagementRoute(
            onBackClick = onBackClick
        )
    }
}

fun NavGraphBuilder.qrScannerScreen(
    onBackClick: () -> Unit,
    onPermissionBlock: () -> Unit
) {
    composable(route = "$qrScannerRoute/{id}/{traineeId}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
        val traineeId = backStackEntry.arguments?.getString("traineeId")?.toLongOrNull()
        if (id != null && traineeId != null) {
            QrScannerRoute(
                id = id,
                traineeId = traineeId,
                onBackClick = onBackClick,
                onPermissionBlock = onPermissionBlock
            )
        }
    }
}