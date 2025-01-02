package com.school_of_company.program.util

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
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executors

fun setupQrScanCamera(
    previewView: PreviewView,
    lifecycleOwner: LifecycleOwner,
    onQrcodeScanned: (Long) -> Unit
) {
    val context = previewView.context
    val cameraExecutor = Executors.newSingleThreadExecutor()
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()

        val preview = Preview.Builder()
            .build()
            .apply {
                surfaceProvider = previewView.surfaceProvider
            }

        val imageCapture = ImageCapture.Builder().build()

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
                setAnalyzer(cameraExecutor, QrcodeAnalyzer { qrcodeData ->
                    onQrcodeScanned(qrcodeData)
                })
            }

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()

            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture,
                imageAnalyzer
            )

            val cameraControl = camera.cameraControl

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
}