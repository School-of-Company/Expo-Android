package com.school_of_company.domain.usecase.trainee

import com.school_of_company.data.repository.trainee.TraineeRepository
import com.school_of_company.model.entity.trainee.TraineeResponseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TraineeResponseListUseCase @Inject constructor(
    private val repository: TraineeRepository
) {
    operator fun invoke(
        expoId: String,
        name: String? = null
    ): Flow<List<TraineeResponseEntity>> =
        repository.getTraineeList(
            expoId = expoId,
            name = name
        )
}