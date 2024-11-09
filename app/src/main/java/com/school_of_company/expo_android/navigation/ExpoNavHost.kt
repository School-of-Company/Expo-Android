package com.school_of_company.expo_android.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.school_of_company.common.exception.*
import com.school_of_company.design_system.R
import com.school_of_company.expo_android.ui.ExpoAppState
import com.school_of_company.home.navigation.homeDetailScreen
import com.school_of_company.home.navigation.homeScreen
import com.school_of_company.home.navigation.homeSendMessageScreen
import com.school_of_company.home.navigation.navigateToHome
import com.school_of_company.home.navigation.navigateToHomeDetail
import com.school_of_company.home.navigation.navigateToHomeSendMessage
import com.school_of_company.navigation.navigateToSignIn
import com.school_of_company.navigation.sigInRoute
import com.school_of_company.navigation.signInScreen
import com.school_of_company.signup.navigation.navigationToSignUp
import com.school_of_company.signup.navigation.signUpRoute
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

        // 화면을 들어갈 때의 애니메이션 - forward navigation
        enterTransition = {
            // mainRoute가 아니라면 왼쪽에서 슬라이드하여 나타나게 됨
            if (targetState.destination.route != "") { // add mainRoute
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, // 왼쪽에서 들어옴
                    animationSpec = tween(durationMillis = 500) // 500 밀리초 동안 진행
                )
            } else {
                EnterTransition.None
            }
        },
        // 화면을 나갈 때의 애니메이션 - forward navigation
        exitTransition = {
            // mainRoute가 아니라면 왼쪽으로 슬라이드하여 사라지게 됨
            if (targetState.destination.route != "") { // add mainRoute
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, // 왼쪽으로 나감
                    animationSpec = tween(durationMillis = 500) // 500 밀리초 동안 진행
                )
            } else {
                ExitTransition.None
            }
        },
        // 이전 화면으로 돌아갈 때의 애니메이션 - back navigation
        popEnterTransition = {
            // 오른쪽에서 슬라이드하여 화면이 들어옴
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, // 오른쪽에서 들어옴
                animationSpec = tween(durationMillis = 350) // 350 밀리초 동안 진행
            )
        },
        // 이전 화면으로 돌아갈 때의 애니메이션 설정 - back navigation
        popExitTransition = {
            // 화면이 오른쪽으로 슬라이드하며 나감
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, // 오른쪽으로 나가는 슬라이드
                animationSpec = tween(durationMillis = 350) // 350 밀리초 동안 진행
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

        homeScreen(
            navigationToDetail = navController::navigateToHomeDetail
        )

        homeDetailScreen(
            onBackClick = navController::popBackStack,
            onMessageClick = navController::navigateToHomeSendMessage,
            onCheckClick = {},
            onQrGenerateClick = {},
            onModifyClick = {},
            onProgramClick = {}
        )

        homeSendMessageScreen(
            onBackClick = navController::popBackStack,
            onSendClick = navController::navigateToHomeDetail
        )
    }
}