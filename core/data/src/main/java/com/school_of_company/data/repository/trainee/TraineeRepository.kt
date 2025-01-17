package com.school_of_company.data.repository.trainee

import com.school_of_company.model.entity.trainee.TraineeResponseEntity
import kotlinx.coroutines.flow.Flow

interface TraineeRepository {
    fun getTraineeList(expoId: String): Flow<List<TraineeResponseEntity>>
}