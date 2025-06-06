package com.school_of_company.expo.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

internal fun getMultipartFile(
    context: Context,
    uri: Uri
): MultipartBody.Part? {
    val jpegFile = uriToJpeg(context, uri) ?: return null

    return fileToMultipartFile(jpegFile)
}

private fun uriToJpeg(
    context: Context,
    uri: Uri
): File? {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val bitmap = getExifData(context = context, uri = uri)?.let { exifData ->
        rotateImage(BitmapFactory.decodeStream(inputStream), exifData)
    } ?: BitmapFactory.decodeStream(inputStream)
    inputStream.close()

    val outputFile = createTempJpegFile(context) ?: return null

    val outputStream: OutputStream = FileOutputStream(outputFile)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
    outputStream.close()
    bitmap.recycle()

    return outputFile
}

private fun fileToMultipartFile(file: File): MultipartBody.Part {
    val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())

    return MultipartBody.Part.createFormData("image", file.name, requestFile)
}

@Throws(IOException::class)
private fun createTempJpegFile(context: Context): File? {
    val outputDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "image_",
        ".jpg",
        outputDirectory
    )
}