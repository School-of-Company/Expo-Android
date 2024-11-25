package com.school_of_company.domain.usecase.training

import com.school_of_company.data.repository.training.TrainingRepository
import com.school_of_company.model.model.training.TrainingDtoModel
import javax.inject.Inject

class RegisterTrainingProgramUseCase @Inject constructor(
    private val repository: TrainingRepository
) {
    operator fun invoke(
        expoId: String,
        body: TrainingDtoModel
    ) = runCatching {
        repository.registerTrainingProgram(
            expoId = expoId,
            body = body
        )
    }
}