package com.school_of_company.data.repository.juso

import com.school_of_company.model.model.juso.JusoModel
import com.school_of_company.network.datasource.juso.AddressDataSource
import com.school_of_company.network.mapper.juso.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val addressDataSource: AddressDataSource,
) : AddressRepository {
    companion object {
        private const val DEFAULT_PAGE = 1
        private const val DEFAULT_PAGE_SIZE = 5
    }

    override fun getAddress(keyword: String): Flow<List<JusoModel>> =
        addressDataSource.getAddress(
            countPerPage = DEFAULT_PAGE,
            currentPage = DEFAULT_PAGE_SIZE,
            keyword = keyword
        ).transform { list ->
            emit(list.results.juso?.map { juso -> juso.toModel() } ?: emptyList())
        }
}