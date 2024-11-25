package com.school_of_company.network.datasource.training

import com.school_of_company.network.dto.training.response.TeacherTrainingProgramResponse
import com.school_of_company.network.dto.training.all.TrainingDto
import com.school_of_company.network.dto.training.response.TrainingProgramListResponse
import kotlinx.coroutines.flow.Flow

interface TrainingDataSource {
    fun registerTrainingProgram(expoId: String, body: TrainingDto) : Flow<Unit>
    fun registerTrainingProgramList(expoId: String, body: List<TrainingDto>) : Flow<Unit>
    fun modifyTrainingProgram(trainingProId: Long, body: TrainingDto) : Flow<Unit>
    fun deleteTrainingProgram(trainingProId: Long) : Flow<Unit>
    fun trainingProgramList(expoId: String) : Flow<List<TrainingProgramListResponse>>
    fun teacherTrainingProgramList(trainingProId: Long) : Flow<List<TeacherTrainingProgramResponse>>
}