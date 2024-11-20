package com.school_of_company.network.dto.image

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageUpLoadResponse(
    @Json(name = "imageURL") val imageURL: String,
)
