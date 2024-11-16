package com.school_of_company.expo_android.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.school_of_company.common.exception.*
import com.school_of_company.design_system.R
import com.school_of_company.expo.navigation.expoCreateScreen
import com.school_of_company.expo.navigation.expoDetailScreen
import com.school_of_company.expo.navigation.expoModifyScreen
import com.school_of_company.expo.navigation.expoScreen
import com.school_of_company.expo.navigation.homeRoute
import com.school_of_company.expo.navigation.navigateToExpoDetail
import com.school_of_company.expo.navigation.navigateToExpoModify
import com.school_of_company.expo.navigation.navigateToHome
import com.school_of_company.expo_android.ui.ExpoAppState
import com.school_of_company.home.navigation.homeDetailParticipantManagementScreen
import com.school_of_company.home.navigation.homeDetailProgramParticipantScreen
import com.school_of_company.home.navigation.homeDetailProgramScreen
import com.school_of_company.home.navigation.homeSendMessageScreen
import com.school_of_company.home.navigation.navigateToHomeDetailParticipantManagement
import com.school_of_company.home.navigation.navigateToHomeDetailProgram
import com.school_of_company.home.navigation.navigateToHomeDetailProgramParticipant
import com.school_of_company.home.navigation.navigateToHomeSendMessage
import com.school_of_company.navigation.navigateToSignIn
import com.school_of_company.navigation.sigInRoute
import com.school_of_company.navigation.signInScreen
import com.school_of_company.signup.navigation.navigationToSignUp
import com.school_of_company.signup.navigation.signUpScreen
import com.school_of_company.ui.toast.makeToast

@Composable
fun ExpoNavHost(
    modifier: Modifier = Modifier,
    appState: ExpoAppState, // 네비게이션의 상태를 포함하는 앱의 상태
    startDestination: String = sigInRoute
) {
    val navController = appState.navController
    val context = LocalContext.current

    val makeErrorToast: (throwable: Throwable?, message: Int?) -> Unit = { throwable, message ->
        val errorMessage = throwable?.let {
            when(it) {
                is ForbiddenException -> R.string.error_for_bidden
                is TimeOutException -> R.string.error_time_out
                is ServerException -> R.string.error_server
                is NoInternetException -> R.string.error_no_internet
                is OtherHttpException -> R.string.error_other_http
                is UnKnownException -> R.string.error_un_known
                else -> message
            }
        } ?: message ?: R.string.error_default
        makeToast(context, context.getString(errorMessage))
    }
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,

        enterTransition = {
            // homeRoute로 이동할 때도 애니메이션 적용
            if (targetState.destination.route == homeRoute) {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, // 오른쪽에서 들어옴
                    animationSpec = tween(durationMillis = 500)
                )
            } else {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, // 왼쪽에서 들어옴
                    animationSpec = tween(durationMillis = 500)
                )
            }
        },

        // 화면을 나갈 때의 애니메이션 - forward navigation
        exitTransition = {
            if (targetState.destination.route == homeRoute) {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, // 오른쪽으로 나감
                    animationSpec = tween(durationMillis = 500)
                )
            } else {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, // 왼쪽으로 나감
                    animationSpec = tween(durationMillis = 500)
                )
            }
        },

        // 이전 화면으로 돌아갈 때의 애니메이션 - back navigation
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = 350)
            )
        },

        // 이전 화면으로 돌아갈 때의 애니메이션 설정 - back navigation
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = 350)
            )
        }
    ) {
        signInScreen(
            onSignUpClick = navController::navigationToSignUp,
            onSignInClick = navController::navigateToHome,
            onErrorToast = makeErrorToast
        )

        signUpScreen(
            onSignUpClick = navController::navigateToSignIn,
            onBackClicked = navController::popBackStack,
            onErrorToast = makeErrorToast
        )

        expoScreen(
            navigationToDetail = navController::navigateToExpoDetail
        )

        expoDetailScreen(
            onBackClick = navController::popBackStack,
            onMessageClick = navController::navigateToHomeSendMessage,
            onCheckClick = navController::navigateToHomeDetailParticipantManagement,
            onQrGenerateClick = {},
            onModifyClick = navController::navigateToExpoModify,
            onProgramClick = navController::navigateToHomeDetailProgram
        )

        homeSendMessageScreen(
            onBackClick = navController::popBackStack,
            onSendClick = navController::navigateToExpoDetail
        )

        homeDetailProgramScreen(
            onBackClick = navController::popBackStack,
            navigateToProgramDetail = navController::navigateToHomeDetailProgramParticipant
        )

        homeDetailProgramParticipantScreen(
            onBackClick = navController::popBackStack
        )

        homeDetailParticipantManagementScreen(
            onBackClick = navController::popBackStack
        )

        expoModifyScreen(
            onBackClick = navController::popBackStack,
            onModifyClick = navController::navigateToExpoDetail
        )

        expoCreateScreen(
            onExpoCreateClick = navController::navigateToHome
        )
    }
}