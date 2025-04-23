package com.school_of_company.network.datasource.juso

import com.school_of_company.network.BuildConfig
import com.school_of_company.network.api.AddressAPI
import com.school_of_company.network.dto.juso.AddressResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddressDataSourceImpl @Inject constructor(
    private val service: AddressAPI,
) : AddressDataSource {
    override fun getAddress(
        currentPage: Int,
        countPerPage: Int,
        keyword: String,
    ): Flow<AddressResponse> = performApiRequest {
        service.getAddress(
            confmKey = BuildConfig.ADDRESS_API_KEY,
            currentPage = currentPage,
            countPerPage = countPerPage,
            keyword = keyword,
            resultType = "json"
        )
    }
}