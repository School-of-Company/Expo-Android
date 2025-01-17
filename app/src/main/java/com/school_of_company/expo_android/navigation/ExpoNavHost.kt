package com.school_of_company.expo_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.school_of_company.common.exception.*
import com.school_of_company.design_system.R
import com.school_of_company.expo.navigation.expoCreateScreen
import com.school_of_company.expo.navigation.expoCreatedScreen
import com.school_of_company.expo.navigation.expoDetailScreen
import com.school_of_company.expo.navigation.expoModifyScreen
import com.school_of_company.expo.navigation.expoScreen
import com.school_of_company.expo.navigation.navigateToExpoDetail
import com.school_of_company.expo.navigation.navigateToExpoModify
import com.school_of_company.expo.navigation.navigateToHome
import com.school_of_company.expo_android.ui.ExpoAppState
import com.school_of_company.expo_android.ui.navigationPopUpToLogin
import com.school_of_company.program.navigation.navigateQrScanner
import com.school_of_company.program.navigation.qrScannerScreen
import com.school_of_company.navigation.navigateToSignIn
import com.school_of_company.navigation.sigInRoute
import com.school_of_company.navigation.signInScreen
import com.school_of_company.program.navigation.navigateToProgramDetailParticipantManagement
import com.school_of_company.program.navigation.navigateToProgramDetailProgram
import com.school_of_company.program.navigation.programDetailParticipantManagementScreen
import com.school_of_company.program.navigation.programDetailProgramScreen
import com.school_of_company.program.util.QrReadScreenType
import com.school_of_company.signup.navigation.navigationToSignUp
import com.school_of_company.signup.navigation.signUpScreen
import com.school_of_company.sms.navigation.navigateToSmsSendMessage
import com.school_of_company.sms.navigation.smsSendMessageScreen
import com.school_of_company.standard.navigation.navigateToStandardProgramParticipant
import com.school_of_company.standard.navigation.standardProgramParticipantScreen
import com.school_of_company.training.navigation.navigateToTrainingProgramParticipant
import com.school_of_company.training.navigation.trainingProgramParticipantScreen
import com.school_of_company.ui.toast.makeToast
import com.school_of_company.user.navigation.profileScreen

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
            when (it) {
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
            onMessageClick = navController::navigateToSmsSendMessage,
            onCheckClick = navController::navigateToProgramDetailParticipantManagement,
            onModifyClick = { id ->
                navController.navigateToExpoModify(id)
            },
            onProgramClick = { id ->
                navController.navigateToProgramDetailProgram(id)
            }
        )

        smsSendMessageScreen(
            onErrorToast = makeErrorToast,
            onBackClick = navController::popBackStack,
        )

        programDetailProgramScreen(
            onBackClick = navController::popBackStack,
            navigateToTrainingProgramDetail = { id ->
                navController.navigateToTrainingProgramParticipant(id)
            },
            navigateToStandardProgramDetail = { id ->
                navController.navigateToStandardProgramParticipant(id)
            }
        )

        trainingProgramParticipantScreen(
            onBackClick = navController::popBackStack,
            navigateToQrScanner = { id ->
                navController.navigateQrScanner(
                    id = id,
                    screenType = QrReadScreenType.TrainingProgramParticipantScreen.routeName
                )
            }
        )

        standardProgramParticipantScreen(
            onBackClick = navController::popBackStack,
            navigateToQrScanner = { id ->
                navController.navigateQrScanner(
                    id = id,
                    screenType = QrReadScreenType.StandardProgramParticipantRoute.routeName
                )
            }
        )

        programDetailParticipantManagementScreen(
            onBackClick = navController::popBackStack
        )

        expoModifyScreen(
            onErrorToast = makeErrorToast,
            onBackClick = navController::popBackStack,
        )

        expoCreateScreen(
            onErrorToast = makeErrorToast
        )

        expoCreatedScreen(
            onErrorToast = makeErrorToast
        )

        qrScannerScreen(
            onBackClick = navController::popBackStack,
            onPermissionBlock = navController::popBackStack,
            onErrorToast = makeErrorToast
        )

        profileScreen(
            onErrorToast = makeErrorToast,
            onMainNavigate = { navController.navigationPopUpToLogin(loginRoute = sigInRoute) }
        )
    }
}