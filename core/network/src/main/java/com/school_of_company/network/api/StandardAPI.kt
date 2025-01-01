package com.school_of_company.network.api

import com.school_of_company.network.dto.standard.request.StandardRequest
import com.school_of_company.network.dto.standard.response.StandardAttendListResponse
import com.school_of_company.network.dto.standard.response.StandardProgramListResponse
import retrofit2.http.*

interface StandardAPI {

    @POST("/standard/{expo_id}")
    suspend fun registerStandardProgram(
        @Path("expo_id") expoId: String,
        @Body body: StandardRequest
    )

    @POST("/standard/list/{expo_id}")
    suspend fun registerStandardListProgram(
        @Path("expo_id") expoId: String,
        @Body body: List<StandardRequest>
    )

    @PATCH("/standard/{standardPro_id}")
    suspend fun modifyStandardProgram(
        @Path("standardPro_id") standardProId: Long,
        @Body body: StandardRequest
    )

    @DELETE("/standard/{standardPro_id}")
    suspend fun deleteStandardProgram(
        @Path("standardPro_id") standardProId: Long
    )

    @GET("/standard/program/{expo_id}")
    suspend fun standardProgramList(
        @Path("expo_id") expoId: String
    ) : List<StandardProgramListResponse>

    @GET("/standard/{standardPro_id}")
    suspend fun standardProgramAttendList(
        @Path("standardPro_id") standardProId: Long
    ) : List<StandardAttendListResponse>
}