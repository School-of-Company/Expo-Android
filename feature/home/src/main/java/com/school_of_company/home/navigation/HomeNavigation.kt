package com.school_of_company.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.home.view.HomeDetailParticipantManagementRoute
import com.school_of_company.home.view.HomeDetailProgramParticipantRoute
import com.school_of_company.home.view.HomeDetailProgramRoute
import com.school_of_company.home.view.SendMessageRoute

const val homeSendMessageRoute = "home_send_message_route"
const val homeDetailProgramRoute = "home_detail_program_route"
const val homeDetailProgramParticipantRoute = "home_detail_program_participant_route"
const val homeDetailParticipantManagementRoute = "home_detail_participant_management_route"

fun NavController.navigateToHomeSendMessage(navOptions: NavOptions? = null) {
    this.navigate(homeSendMessageRoute, navOptions)
}

fun NavController.navigateToHomeDetailProgram(navOptions: NavOptions? = null) {
    this.navigate(homeDetailProgramRoute, navOptions)
}

fun NavController.navigateToHomeDetailProgramParticipant(navOptions: NavOptions? = null) {
    this.navigate(homeDetailProgramParticipantRoute, navOptions)
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

fun NavGraphBuilder.homeDetailProgramScreen(
    onBackClick: () -> Unit,
    navigateToProgramDetail: () -> Unit
) {
    composable(route = homeDetailProgramRoute) {
        HomeDetailProgramRoute(
            onBackClick = onBackClick,
            navigateToProgramDetail = navigateToProgramDetail
        )
    }
}

fun NavGraphBuilder.homeDetailProgramParticipantScreen(
    onBackClick: () -> Unit
) {
    composable(route = homeDetailProgramParticipantRoute) {
        HomeDetailProgramParticipantRoute(
            onBackClick = onBackClick
        )
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