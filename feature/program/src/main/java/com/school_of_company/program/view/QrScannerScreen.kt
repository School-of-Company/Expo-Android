package com.school_of_company.program.view

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.loading.LoadingDot
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.icon.QrGuideImage
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.param.attendance.StandardQrCodeRequestParam
import com.school_of_company.program.view.component.QrcodeScanView
import com.school_of_company.program.viewmodel.ProgramViewModel
import com.school_of_company.program.viewmodel.uistate.ReadQrCodeUiState
import com.school_of_company.model.param.attendance.TrainingQrCodeRequestParam
import com.school_of_company.program.util.QrReadScreenType
import com.school_of_company.program.util.parseStandardQrScanModel
import com.school_of_company.program.util.parseTrainingQr
import com.school_of_company.program.view.component.StableLifecycleOwner
import com.school_of_company.ui.toast.makeToast
import kotlinx.coroutines.delay

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun QrScannerRoute(
    modifier: Modifier = Modifier,
    id: Long,
    screenType: String,
    onBackClick: () -> Unit,
    onPermissionBlock: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: ProgramViewModel = hiltViewModel(),
) {
    var showScanner by rememberSaveable { mutableStateOf(false) }

    val trainingQrCodeUiState by viewModel.readQrCodeUiState.collectAsStateWithLifecycle()

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val context = LocalContext.current

    LaunchedEffect(cameraPermissionState.status) {
        when {
            cameraPermissionState.status.isGranted -> {
                showScanner = true
            }

            !cameraPermissionState.status.isGranted &&
                    !cameraPermissionState.status.shouldShowRationale -> {
                cameraPermissionState.launchPermissionRequest()
            }

            else -> {
                onErrorToast(null, R.string.camera_access_allow_in_setting)
            }
        }
    }

    if (cameraPermissionState.status.isGranted) {
        LaunchedEffect(trainingQrCodeUiState) {
            when (trainingQrCodeUiState) {
                is ReadQrCodeUiState.Loading -> {
                    makeToast(context, "로딩중..")
                }

                is ReadQrCodeUiState.Success -> {
                    makeToast(context, "인식 성공!")
                    onBackClick()
                }

                is ReadQrCodeUiState.Error -> {
                    makeToast(context, "인식을 하지 못하였습니다.")
                }
            }
        }
    }

    when {
        showScanner -> {
            QrScannerScreen(
                modifier = modifier,
                onBackClick = onBackClick,
                onQrcodeScan = { jsonData ->
                    when (screenType) {
                        QrReadScreenType.TrainingProgramParticipantScreen.routeName -> {
                            viewModel.trainingQrCode(
                                trainingId = id,
                                body = TrainingQrCodeRequestParam(
                                    traineeId = jsonData.parseTrainingQr().toLong()
                                )
                            )
                        }

                        QrReadScreenType.StandardProgramParticipantRoute.routeName -> {
                            val parsedData = jsonData.parseStandardQrScanModel()
                            viewModel.standardQrCode(
                                standardId = id,
                                body = StandardQrCodeRequestParam(
                                    participantId = parsedData.participantId,
                                    phoneNumber = parsedData.phoneNumber
                                )
                            )
                        }
                    }
                }
            )
        }

        cameraPermissionState.status.shouldShowRationale -> {
            onPermissionBlock()
        }
    }
}

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
private fun QrScannerScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onQrcodeScan: (String) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    var qrSettingCountdown by rememberSaveable { mutableStateOf(2) }
    var showCountdown by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (qrSettingCountdown > 0) {
            delay(1000)
            qrSettingCountdown--
        }
        showCountdown = false
    }

    ExpoAndroidTheme { colors, _ ->

        Box(contentAlignment = Alignment.Center) {

            if (showCountdown) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f))
                ) {
                    LoadingDot()
                }
            } else {

                QrcodeScanView(
                    onQrcodeScan = onQrcodeScan,
                    lifecycleOwner = StableLifecycleOwner(lifecycleOwner),
                )

                QrGuideImage()

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp)
                ) {
                    ExpoTopBar(
                        startIcon = {
                            LeftArrowIcon(
                                tint = colors.white,
                                modifier = Modifier
                                    .expoClickable { onBackClick() }
                                    .padding(top = 16.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}