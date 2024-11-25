package com.school_of_company.network.datasource.training

import com.school_of_company.network.api.TrainingAPI
import com.school_of_company.network.dto.training.response.TeacherTrainingProgramResponse
import com.school_of_company.network.dto.training.all.TrainingDto
import com.school_of_company.network.dto.training.response.TrainingProgramListResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrainingDataSourceImpl @Inject constructor(
    private val service: TrainingAPI
) : TrainingDataSource {
    override fun registerTrainingProgram(
        expoId: String,
        body: TrainingDto
    ): Flow<Unit> =
        performApiRequest { service.registerTrainingProgram(
            expoId = expoId,
            body = body
        ) }

    override fun registerTrainingProgramList(
        expoId: String,
        body: List<TrainingDto>
    ): Flow<Unit> =
        performApiRequest { service.registerTrainingProgramList(
            expoId = expoId,
            body = body
        ) }

    override fun modifyTrainingProgram(trainingProId: Long, body: TrainingDto): Flow<Unit> =
        performApiRequest { service.modifyTrainingProgram(
            trainingProId = trainingProId,
            body = body
        ) }

    override fun deleteTrainingProgram(trainingProId: Long): Flow<Unit> =
        performApiRequest { service.deleteTrainingProgram(trainingProId = trainingProId) }

    override fun trainingProgramList(expoId: String): Flow<List<TrainingProgramListResponse>> =
        performApiRequest { service.trainingProgramList(expoId = expoId) }

    override fun teacherTrainingProgramList(trainingProId: Long): Flow<List<TeacherTrainingProgramResponse>> =
        performApiRequest { service.teacherTrainingProgramList(trainingProId = trainingProId) }
}