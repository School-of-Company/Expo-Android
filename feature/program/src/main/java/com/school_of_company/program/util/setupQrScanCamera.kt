package com.school_of_company.program.util

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

internal fun setupQrScanCamera(
    previewView: PreviewView,
    lifecycleOwner: LifecycleOwner,
    onQrcodeScanned: (String) -> Unit
) {
    val context = previewView.context
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()

        try {
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val preview = buildPreview(previewView)
            val imageAnalyzer = buildImageAnalysis(context) { qrCodeData ->
                    qrCodeData?.takeIf { it.isNotEmpty() }?.let { onQrcodeScanned(it) }
                }

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalyzer
            )
        } catch (e: Exception) {
            Log.e("QrcodeScanView", "Camera binding failed", e)
        }
    }, ContextCompat.getMainExecutor(context))
}