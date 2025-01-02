package com.school_of_company.program.view.component

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.school_of_company.program.util.setupQrScanCamera

@Composable
internal fun QrcodeScanView(
    modifier: Modifier = Modifier,
    onQrcodeScan: (Long) -> Unit,
    lifecycleOwner: LifecycleOwner
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            PreviewView(context).apply {
                scaleType = PreviewView.ScaleType.FILL_CENTER
                post {
                    setupQrScanCamera(
                        previewView = this,
                        lifecycleOwner = lifecycleOwner,
                        onQrcodeScanned = onQrcodeScan
                    )
                }
            }
        },
    )
}
