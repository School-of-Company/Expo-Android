package com.school_of_company.program.util

import androidx.camera.core.ImageAnalysis
import androidx.core.content.ContextCompat

internal fun buildImageAnalysis(
    context: android.content.Context,
    onQrcodeScanned: (String?) -> Unit
): ImageAnalysis {
    return ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build().apply {
            setAnalyzer(ContextCompat.getMainExecutor(context), QrcodeAnalyzer(onQrcodeScanned))
        }
}
