package com.school_of_company.network.datasource.trainee

import com.school_of_company.network.api.TraineeAPI
import com.school_of_company.network.dto.trainee.response.TraineeResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TraineeDataSourceImpl @Inject constructor(
    private val service: TraineeAPI
) : TraineeDataSource {
    override fun getTraineeList(expoId: String): Flow<List<TraineeResponse>> =
        performApiRequest { service.getTraineeList(expoId = expoId) }
}