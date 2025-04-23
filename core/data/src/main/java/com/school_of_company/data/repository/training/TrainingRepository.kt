package com.school_of_company.data.repository.training

import com.school_of_company.model.entity.training.TeacherTrainingProgramResponseEntity
import com.school_of_company.model.entity.training.TrainingProgramListResponseEntity
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {
    fun deleteTrainingProgram(trainingProId: Long) : Flow<Unit>
    fun trainingProgramList(expoId: String) : Flow<List<TrainingProgramListResponseEntity>>
    fun teacherTrainingProgramList(trainingProId: Long) : Flow<List<TeacherTrainingProgramResponseEntity>>
}