package com.school_of_company.data.repository.juso

import com.school_of_company.model.model.juso.JusoModel
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    fun getAddress(
        currentPage: Int,
        countPerPage: Int,
        keyword: String,
    ): Flow<List<JusoModel>>
}