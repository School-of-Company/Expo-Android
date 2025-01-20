package com.school_of_company.network.api

import com.school_of_company.network.dto.trainee.response.TraineeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TraineeAPI {

    @GET("/trainee/{expo_id}")
    suspend fun getTraineeList(
        @Path("expo_id") expoId: String,
        @Query("name") name: String? = null
    ) : List<TraineeResponse>
}