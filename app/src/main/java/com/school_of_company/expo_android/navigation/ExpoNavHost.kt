package com.school_of_company.expo_android.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.school_of_company.common.exception.*
import com.school_of_company.design_system.R
import com.school_of_company.expo.navigation.expoAddressSearchScreen
import com.school_of_company.expo.navigation.expoCreateRoute
import com.school_of_company.expo.navigation.expoCreateScreen
import com.school_of_company.expo.navigation.expoCreatedRoute
import com.school_of_company.expo.navigation.expoCreatedScreen
import com.school_of_company.expo.navigation.expoDetailScreen
import com.school_of_company.expo.navigation.expoModifyScreen
import com.school_of_company.expo.navigation.expoScreen
import com.school_of_company.expo.navigation.homeRoute
import com.school_of_company.expo.navigation.navigateToExpoAddressSearch
import com.school_of_company.expo.navigation.navigateToExpoDetail
import com.school_of_company.expo.navigation.navigateToExpoModify
import com.school_of_company.expo.navigation.navigateToHome
import com.school_of_company.expo_android.ui.ExpoAppState
import com.school_of_company.expo_android.ui.navigationPopUpToLogin
import com.school_of_company.form.navigation.formCreateScreen
import com.school_of_company.form.navigation.navigationToFormCreate
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
import com.school_of_company.user.navigation.profileRoute
import com.school_of_company.user.navigation.profileScreen

private val ROUTES_WITHOUT_NAV_PADDING = listOf(
    homeRoute,
    expoCreateRoute,
    expoCreatedRoute,
    profileRoute
)

@Composable
fun ExpoNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = sigInRoute,
    appState: ExpoAppState, // 네비게이션의 상태를 포함하는 앱의 상태
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
        modifier = if (navController.currentDestination?.route in ROUTES_WITHOUT_NAV_PADDING) {
            modifier
        } else {
            modifier.windowInsetsPadding(WindowInsets.navigationBars)
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
            onCheckClick = { id ->
                navController.navigateToProgramDetailParticipantManagement(id)
            },
            onModifyClick = { id ->
                navController.navigateToExpoModify(id)
            },
            onProgramClick = { id ->
                navController.navigateToProgramDetailProgram(id)
            },
            onMessageClick = navController::navigateToSmsSendMessage,
            navigationToFormCreate = navController::navigationToFormCreate,
            onErrorToast = makeErrorToast,
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
            navigateToExpoAddressSearch = navController::navigateToExpoAddressSearch,
            onErrorToast = makeErrorToast,
            onBackClick = navController::popBackStack,
        )

        expoCreateScreen(
            navigateToExpoAddressSearch = navController::navigateToExpoAddressSearch,
            onErrorToast = makeErrorToast
        )

        expoCreatedScreen(
            onErrorToast = makeErrorToast
        )

        expoAddressSearchScreen(
            popUpBackStack = navController::popBackStack,
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

        formCreateScreen(
            popUpBackStack = navController::popBackStack,
            onErrorToast = makeErrorToast,
        )
    }
}