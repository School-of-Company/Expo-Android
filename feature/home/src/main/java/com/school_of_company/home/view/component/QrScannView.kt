package com.school_of_company.home.view.component

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.school_of_company.home.util.QrcodeScanner
import java.util.concurrent.Executors

@androidx.camera.core.ExperimentalGetImage
@Composable
internal fun QrcodeScanView(
    onQrcodeScan: (Long) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding: PaddingValues ->
        AndroidView(
            factory = { context ->
                var isScanningEnabled = true

                val cameraExecutor = Executors.newSingleThreadExecutor()
                val previewView = PreviewView(context).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }

                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    // Preview 설정
                    val preview = Preview.Builder()
                        .build()
                        .apply {
                            setSurfaceProvider(previewView.surfaceProvider)
                        }

                    // ImageCapture 설정
                    val imageCapture = ImageCapture.Builder().build()

                    // ImageAnalysis 설정
                    val imageAnalyzer = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .setResolutionSelector(
                            ResolutionSelector.Builder()
                                .setAspectRatioStrategy(
                                    AspectRatioStrategy.RATIO_4_3_FALLBACK_AUTO_STRATEGY
                                )
                                .build()
                        )
                        .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888)
                        .setTargetRotation(previewView.display.rotation)
                        .build()
                        .apply {
                            setAnalyzer(cameraExecutor, QrcodeScanner { qrcodeData ->
                                if (isScanningEnabled) {
                                    isScanningEnabled = false
                                    onQrcodeScan(qrcodeData)
                                }
                            })
                        }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    try {
                        cameraProvider.unbindAll()

                        val camera = cameraProvider.bindToLifecycle(
                            context as LifecycleOwner,
                            cameraSelector,
                            preview,
                            imageCapture,
                            imageAnalyzer
                        )

                        val cameraControl = camera.cameraControl

                        // FocusMeteringAction으로 초점 및 노출 조정
                        val meteringPointFactory = previewView.meteringPointFactory
                        val meteringPoint = meteringPointFactory.createPoint(
                            previewView.width / 2f,
                            previewView.height / 2f
                        )
                        val focusMeteringAction = FocusMeteringAction.Builder(meteringPoint)
                            .setAutoCancelDuration(5, java.util.concurrent.TimeUnit.SECONDS)
                            .build()

                        cameraControl.startFocusAndMetering(focusMeteringAction)
                        cameraControl.setExposureCompensationIndex(0)
                    } catch (e: Exception) {
                        Log.e("QrcodeScanView", "Camera binding failed", e)
                    }
                }, ContextCompat.getMainExecutor(context))

                previewView
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}