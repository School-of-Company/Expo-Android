package com.school_of_company.network.datasource.training

import com.school_of_company.network.dto.training.response.TeacherTrainingProgramResponse
import com.school_of_company.network.dto.training.response.TrainingProgramListResponse
import kotlinx.coroutines.flow.Flow

interface TrainingDataSource {
    fun deleteTrainingProgram(trainingProId: Long) : Flow<Unit>
    fun trainingProgramList(expoId: String) : Flow<List<TrainingProgramListResponse>>
    fun teacherTrainingProgramList(trainingProId: Long) : Flow<List<TeacherTrainingProgramResponse>>
}