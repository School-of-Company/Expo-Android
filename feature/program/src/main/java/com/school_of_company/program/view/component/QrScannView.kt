package com.school_of_company.program.view.component

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.school_of_company.program.util.setupQrScanCamera

@Stable
data class StableLifecycleOwner(val owner: LifecycleOwner)

@Composable
internal fun QrcodeScanView(
    modifier: Modifier = Modifier,
    lifecycleOwner: StableLifecycleOwner,
    onQrcodeScan: (String) -> Unit,
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            PreviewView(context).apply {
                post {
                    setupQrScanCamera(
                        previewView = this,
                        lifecycleOwner = lifecycleOwner.owner,
                        onQrcodeScanned = onQrcodeScan
                    )
                }
            }
        },
    )
}
