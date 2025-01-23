package com.school_of_company.network.datasource.juso

import com.school_of_company.network.BuildConfig
import com.school_of_company.network.api.AddressApi
import com.school_of_company.network.dto.juso.AddressResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddressDataSourceImpl @Inject constructor(
    private val addressApi: AddressApi,
) : AddressDataSource {
    override suspend fun getAddress(
        currentPage: Int,
        countPerPage: Int,
        keyword: String,
    ): Flow<AddressResponse> = performApiRequest {
        addressApi.getAddress(
            confmKey = BuildConfig.ADDRESS_API_KEY,
            currentPage = currentPage,
            countPerPage = countPerPage,
            keyword = keyword,
            resultType = "json"
        )
    }
}