package com.school_of_company.network.datasource.trainee

import com.school_of_company.network.dto.trainee.response.TraineeResponse
import kotlinx.coroutines.flow.Flow

interface TraineeDataSource {
    fun getTraineeList(expoId: String, name: String? = null): Flow<List<TraineeResponse>>
}