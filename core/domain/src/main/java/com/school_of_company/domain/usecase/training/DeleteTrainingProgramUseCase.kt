package com.school_of_company.domain.usecase.training

import com.school_of_company.data.repository.training.TrainingRepository
import javax.inject.Inject

class DeleteTrainingProgramUseCase @Inject constructor(
    private val repository: TrainingRepository
) {
    operator fun invoke(trainingProId: Long) = runCatching {
        repository.deleteTrainingProgram(trainingProId = trainingProId)
    }
}