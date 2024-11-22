package com.school_of_company.network.mapper.image

import com.school_of_company.model.entity.image.ImageUpLoadResponseEntity
import com.school_of_company.network.dto.image.ImageUpLoadResponse

fun ImageUpLoadResponse.toModel(): ImageUpLoadResponseEntity =
    ImageUpLoadResponseEntity(imageURL = this.imageURL)