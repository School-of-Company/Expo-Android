package com.school_of_company.network.api

import com.school_of_company.network.dto.training.response.TeacherTrainingProgramResponse
import com.school_of_company.network.dto.training.all.TrainingDto
import com.school_of_company.network.dto.training.response.TrainingProgramListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TrainingAPI {

    @POST("/training/{expo_id}")
    suspend fun registerTrainingProgram(
        @Path("expo_id") expoId: String,
        @Body body: TrainingDto
    )

    @POST("/training/list/{expo_id}")
    suspend fun registerTrainingProgramList(
        @Path("expo_id") expoId: String,
        @Body body: List<TrainingDto>
    )

    @PATCH("/training/{trainingPro_id}")
    suspend fun modifyTrainingProgram(
        @Path("trainingPro_id") trainingProId: Long,
        @Body body: TrainingDto
    )

    @DELETE("/training/{trainingPro_id}")
    suspend fun deleteTrainingProgram(
        @Path("trainingPro_id") trainingProId: Long
    )

    @GET("/training/program/{expo_id}")
    suspend fun trainingProgramList(
        @Path("expo_id") expoId: String
    ) : List<TrainingProgramListResponse>

    @GET("/training/{trainingPro_id}")
    suspend fun teacherTrainingProgramList(
        @Path("trainingPro_id") trainingProId: Long
    ) : List<TeacherTrainingProgramResponse>
}