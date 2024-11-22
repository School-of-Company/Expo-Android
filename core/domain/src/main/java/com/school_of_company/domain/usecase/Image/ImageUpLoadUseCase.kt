package com.school_of_company.domain.usecase.Image

import com.school_of_company.data.repository.image.ImageRepository
import com.school_of_company.model.entity.image.ImageUpLoadResponseEntity
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageUpLoadUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    operator fun invoke(image: MultipartBody.Part) : Flow<ImageUpLoadResponseEntity> =
        repository.imageUpLoad(image = image)
}