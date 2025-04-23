package com.school_of_company.domain.usecase.training

import com.school_of_company.data.repository.training.TrainingRepository
import com.school_of_company.model.entity.training.TeacherTrainingProgramResponseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TeacherTrainingProgramListUseCase @Inject constructor(
    private val repository: TrainingRepository
) {
    operator fun invoke(trainingProId: Long): Flow<List<TeacherTrainingProgramResponseEntity>> =
        repository.teacherTrainingProgramList(trainingProId = trainingProId)
}