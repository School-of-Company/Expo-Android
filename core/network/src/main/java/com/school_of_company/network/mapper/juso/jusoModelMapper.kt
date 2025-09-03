package com.school_of_company.network.mapper.juso

import com.school_of_company.model.model.juso.JusoModel
import com.school_of_company.network.dto.juso.Juso

fun Juso.toModel(): JusoModel = JusoModel(roadAddr = roadAddr, jibunAddr = jibunAddr)
