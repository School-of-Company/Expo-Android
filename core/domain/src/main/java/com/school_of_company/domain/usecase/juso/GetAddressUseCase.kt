package com.school_of_company.domain.usecase.juso

import com.school_of_company.data.repository.juso.AddressRepository
import com.school_of_company.model.model.juso.JusoModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(
    private val repository: AddressRepository,
) {
    suspend operator fun invoke(searchText: String): Flow<List<JusoModel>> =
        repository.getAddress(
            currentPage = 1,
            countPerPage = 5,
            keyword = searchText
        )
}
