package com.school_of_company.domain.usecase.juso

import com.school_of_company.data.repository.juso.AddressRepository
import com.school_of_company.model.model.juso.JusoModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(
    private val repository: AddressRepository,
) {
    companion object {
        private const val DEFAULT_PAGE = 1
        private const val DEFAULT_PAGE_SIZE = 5
    }

    operator fun invoke(searchText: String): Flow<List<JusoModel>> =
        repository.getAddress(
            currentPage = DEFAULT_PAGE,
            countPerPage = DEFAULT_PAGE_SIZE,
            keyword = searchText,
        )
}
