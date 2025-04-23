package com.school_of_company.domain.usecase.training

import com.school_of_company.data.repository.training.TrainingRepository
import com.school_of_company.model.entity.training.TrainingProgramListResponseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrainingProgramListUseCase @Inject constructor(
    private val repository: TrainingRepository
) {
    operator fun invoke(expoId: String): Flow<List<TrainingProgramListResponseEntity>> =
        repository.trainingProgramList(expoId = expoId)
}