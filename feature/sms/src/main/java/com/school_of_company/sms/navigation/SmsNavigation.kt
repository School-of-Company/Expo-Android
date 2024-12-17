package com.school_of_company.sms.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.sms.view.SendMessageRoute

const val smsSendMessageRoute = "sms_send_message_route"

fun NavController.navigateToSmsSendMessage(navOptions: NavOptions? = null) {
    this.navigate(smsSendMessageRoute, navOptions)
}

fun NavGraphBuilder.smsSendMessageScreen(
    onBackClick: () -> Unit
) {
    composable(route = smsSendMessageRoute) {
        SendMessageRoute(
            onBackClick = onBackClick
        )
    }
}