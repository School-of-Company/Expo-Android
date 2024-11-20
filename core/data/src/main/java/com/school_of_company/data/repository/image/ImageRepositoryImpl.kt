package com.school_of_company.data.repository.image

import com.school_of_company.model.entity.image.ImageUpLoadResponseEntity
import com.school_of_company.network.datasource.image.ImageDataSource
import com.school_of_company.network.mapper.image.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val dataSource: ImageDataSource
) : ImageRepository {
    override fun imageUpLoad(image: MultipartBody.Part): Flow<ImageUpLoadResponseEntity> {
        return dataSource.imageUpLoad(image = image).transform { response ->
            emit(response.toModel())
        }
    }
}