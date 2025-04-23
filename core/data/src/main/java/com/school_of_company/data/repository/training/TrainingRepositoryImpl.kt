package com.school_of_company.data.repository.training

import com.school_of_company.model.entity.training.TeacherTrainingProgramResponseEntity
import com.school_of_company.model.entity.training.TrainingProgramListResponseEntity
import com.school_of_company.network.datasource.training.TrainingDataSource
import com.school_of_company.network.mapper.training.response.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class TrainingRepositoryImpl @Inject constructor(
    private val dataSource: TrainingDataSource
) : TrainingRepository {
    override fun deleteTrainingProgram(trainingProId: Long): Flow<Unit> {
        return dataSource.deleteTrainingProgram(trainingProId = trainingProId)
    }

    override fun trainingProgramList(expoId: String): Flow<List<TrainingProgramListResponseEntity>> {
        return dataSource.trainingProgramList(expoId = expoId).transform { list ->
            emit(list.map { it.toEntity() })
        }
    }

    override fun teacherTrainingProgramList(trainingProId: Long): Flow<List<TeacherTrainingProgramResponseEntity>> {
        return dataSource.teacherTrainingProgramList(trainingProId = trainingProId).transform { list ->
            emit(list.map { it.toEntity() })
        }
    }
}