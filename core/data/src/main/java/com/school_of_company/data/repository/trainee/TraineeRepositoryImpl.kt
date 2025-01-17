package com.school_of_company.data.repository.trainee

import com.school_of_company.model.entity.trainee.TraineeResponseEntity
import com.school_of_company.network.datasource.trainee.TraineeDataSource
import com.school_of_company.network.mapper.trainee.response.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class TraineeRepositoryImpl @Inject constructor(
    private val dataSource: TraineeDataSource
) : TraineeRepository {
    override fun getTraineeList(expoId: String): Flow<List<TraineeResponseEntity>> {
        return dataSource.getTraineeList(expoId = expoId).transform { list ->
            emit(list.map { it.toEntity() })
        }
    }
}