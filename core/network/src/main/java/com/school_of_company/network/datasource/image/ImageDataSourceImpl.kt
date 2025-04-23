package com.school_of_company.network.datasource.image

import com.school_of_company.network.api.ImageAPI
import com.school_of_company.network.dto.image.ImageUpLoadResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageDataSourceImpl @Inject constructor(
    private val service: ImageAPI
) : ImageDataSource {
    override fun imageUpLoad(image: MultipartBody.Part): Flow<ImageUpLoadResponse> =
        performApiRequest { service.imageUpLoad(image = image) }
}