package com.school_of_company.home.util

import android.graphics.Bitmap
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
        val bitmap = imageProxy.toBitmap()
        bitmap.let {
            val image = InputImage.fromBitmap(bitmap, imageProxy.imageInfo.rotationDegrees)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val barcodes = scanner.process(image).await()
                    for (barcode in barcodes) {
                        val rawValue = barcode.displayValue
                        val qrCodeValue = rawValue?.toLongOrNull()
                        if (qrCodeValue != null) {
                            qrcodeData(qrCodeValue)
                            Log.d("qrcode", "QR code scan succeeded: $qrCodeValue")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("qrcode", "Scan failed: ${e.message}")
                } finally {
                    imageProxy.close()
                }
            }
        }
    }

    private fun ImageProxy.toBitmap(): Bitmap? {
        val nv21 = yuv420888ToNv21(this)
        val yuvImage = android.graphics.YuvImage(nv21, android.graphics.ImageFormat.NV21, width, height, null)
        val out = java.io.ByteArrayOutputStream()
        yuvImage.compressToJpeg(android.graphics.Rect(0, 0, width, height), 100, out)
        val byteArray = out.toByteArray()
        return android.graphics.BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    private fun yuv420888ToNv21(image: ImageProxy): ByteArray {
        val yBuffer = image.planes[0].buffer
        val uBuffer = image.planes[1].buffer
        val vBuffer = image.planes[2].buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        return nv21
    }
}
