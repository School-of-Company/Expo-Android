package com.school_of_company.domain.usecase.expo

import com.school_of_company.data.repository.expo.ExpoRepository
import com.school_of_company.model.entity.expo.ExpoListResponseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpoListUseCase @Inject constructor(
    private val repository: ExpoRepository
) {
    operator fun invoke() : Flow<List<ExpoListResponseEntity>> =
        repository.getExpoList()
}