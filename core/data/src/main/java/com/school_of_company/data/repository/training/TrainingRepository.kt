package com.school_of_company.data.repository.training

import com.school_of_company.model.entity.training.TeacherTrainingProgramResponseEntity
import com.school_of_company.model.entity.training.TrainingProgramListResponseEntity
import com.school_of_company.model.model.training.TrainingDtoModel
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {
    fun registerTrainingProgram(expoId: String, body: TrainingDtoModel) : Flow<Unit>
    fun registerTrainingProgramList(expoId: String, body: List<TrainingDtoModel>) : Flow<Unit>
    fun modifyTrainingProgram(trainingProId: Long, body: TrainingDtoModel) : Flow<Unit>
    fun deleteTrainingProgram(trainingProId: Long) : Flow<Unit>
    fun trainingProgramList(expoId: String) : Flow<List<TrainingProgramListResponseEntity>>
    fun teacherTrainingProgramList(trainingProId: Long) : Flow<List<TeacherTrainingProgramResponseEntity>>
}