package com.school_of_company.network.datasource.juso

import com.school_of_company.network.dto.juso.AddressResponse
import kotlinx.coroutines.flow.Flow

interface AddressDataSource {
    suspend fun getAddress(
        currentPage: Int,
        countPerPage: Int,
        keyword: String,
    ): Flow<AddressResponse>
}