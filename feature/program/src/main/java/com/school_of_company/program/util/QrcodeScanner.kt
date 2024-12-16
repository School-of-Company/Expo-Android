package com.school_of_company.home.util

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class QrcodeScanner(
    private val qrcodeData: (Long) -> Unit
) : ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_CODE_128,
                Barcode.FORMAT_CODE_39
            )
            .build()
    )

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val barcodes = scanner.process(image).await()
                    for (barcode in barcodes) {
                        val rawValue = barcode.displayValue
                        Log.d("qrcode", "Raw QR code value: $rawValue")

                        if (rawValue != null) {
                            try {
                                val qrCodeValue = rawValue.toLong()
                                qrcodeData(qrCodeValue)
                                Log.d("qrcode", "QR code scan succeeded: $qrCodeValue")
                            } catch (e: NumberFormatException) {
                                Log.d("qrcode", "QR code contains non-numeric value: $rawValue")
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("qrcode", "Scan failed: ${e.message}")
                } finally {
                    imageProxy.close()
                }
            }
        } else {
            imageProxy.close()
        }
    }
}
