package com.school_of_company.sms.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.sms.view.SendMessageRoute

const val smsSendMessageRoute = "sms_send_message_route"

fun NavController.navigateToSmsSendMessage(
    id: String,
    smsType: String,
    navOptions: NavOptions? = null,
) {
    this.navigate(
        "$smsSendMessageRoute/${id}/${smsType}",
        navOptions,
    )
}

fun NavGraphBuilder.smsSendMessageScreen(
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(route = "$smsSendMessageRoute/{id}/{smsType}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val smsType = backStackEntry.arguments?.getString("smsType")

        if (smsType != null) {
            SendMessageRoute(
                id = id,
                smsType = smsType,
                onBackClick = onBackClick,
                onErrorToast = onErrorToast,
            )
        }
    }
}