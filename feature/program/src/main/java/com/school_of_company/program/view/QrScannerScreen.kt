package com.school_of_company.program.view

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.param.attendance.StandardQrCodeRequestParam
import com.school_of_company.program.view.component.QrcodeScanView
import com.school_of_company.program.viewmodel.ProgramViewModel
import com.school_of_company.program.viewmodel.uistate.ReadQrCodeUiState
import com.school_of_company.model.param.attendance.TrainingQrCodeRequestParam
import com.school_of_company.program.util.QrReadScreenType
import com.school_of_company.program.util.QrScanModel
import com.school_of_company.ui.toast.makeToast

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun QrScannerRoute(
    id: Long,
    screenType: String,
    onBackClick: () -> Unit,
    onPermissionBlock: () -> Unit,
    viewModel: ProgramViewModel = hiltViewModel(),
) {
    val trainingQrCodeUiState by viewModel.readQrCodeUiState.collectAsStateWithLifecycle()

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val context = LocalContext.current
    val lifecycleOwner = context as? LifecycleOwner
        ?: throw IllegalStateException("Context is not a LifecycleOwner")

    LaunchedEffect("getPermission") {
        if (!cameraPermissionState.status.isGranted && !cameraPermissionState.status.shouldShowRationale) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

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

    if (cameraPermissionState.status.isGranted) {
        QrScannerScreen(
            onBackClick = onBackClick,
            lifecycleOwner = lifecycleOwner,
            onQrcodeScan = { qrScanModel ->
                when (screenType) {
                    QrReadScreenType.TrainingProgramParticipantScreen.routeName -> {
                        viewModel.trainingQrCode(
                            trainingId = id,
                            body = TrainingQrCodeRequestParam(traineeId = qrScanModel.participantId)
                        )
                    }

                    QrReadScreenType.StandardProgramParticipantRoute.routeName -> {
                        viewModel.standardQrCode(
                            standardId = id,
                            body = StandardQrCodeRequestParam(
                                participantId = qrScanModel.participantId,
                                phoneNumber = qrScanModel.phoneNumber
                            )
                        )
                    }
                }
            }
        )
    } else {
        onPermissionBlock()
    }
}

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
internal fun QrScannerScreen(
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner,
    onBackClick: () -> Unit,
    onQrcodeScan: (QrScanModel) -> Unit,
) {
    ExpoAndroidTheme { colors, _ ->

        QrcodeScanView(
            onQrcodeScan = onQrcodeScan,
            lifecycleOwner = lifecycleOwner,
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .statusBarsPadding()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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