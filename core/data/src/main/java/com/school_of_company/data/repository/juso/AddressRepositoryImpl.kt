package com.school_of_company.data.repository.juso

import com.school_of_company.model.model.juso.JusoModel
import com.school_of_company.network.datasource.juso.AddressDataSource
import com.school_of_company.network.mapper.juso.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val addressDataSource: AddressDataSource
) : AddressRepository {
    override fun getAddress(
        currentPage: Int,
        countPerPage: Int,
        keyword: String
    ): Flow<List<JusoModel>> =
        addressDataSource.getAddress(
            countPerPage = countPerPage,
            currentPage = currentPage,
            keyword = keyword
        ).map {
            it.results.juso?.map { juso -> juso.toModel() } ?: emptyList()
        }
}