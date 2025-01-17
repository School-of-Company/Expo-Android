package com.school_of_company.network.api

import com.school_of_company.network.dto.trainee.response.TraineeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TraineeAPI {

    @GET("/trainee")
    suspend fun getTraineeList(
        @Path("expo_id") expoId: String
    ) : List<TraineeResponse>
}