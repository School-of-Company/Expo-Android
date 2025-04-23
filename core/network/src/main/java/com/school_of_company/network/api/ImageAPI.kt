package com.school_of_company.network.api

import com.school_of_company.network.dto.image.ImageUpLoadResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageAPI {

    @Multipart
    @POST("/image")
    suspend fun imageUpLoad(
        @Part image: MultipartBody.Part
    ) : ImageUpLoadResponse
}