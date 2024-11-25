package com.school_of_company.domain.usecase.training

import com.school_of_company.data.repository.training.TrainingRepository
import com.school_of_company.model.model.training.TrainingDtoModel
import javax.inject.Inject

class ModifyTrainingProgramUseCase @Inject constructor(
    private val repository: TrainingRepository
) {
    operator fun invoke(
        trainingProId: Long,
        body: TrainingDtoModel
    ) = runCatching {
        repository.modifyTrainingProgram(
            trainingProId = trainingProId,
            body = body
        )
    }
}