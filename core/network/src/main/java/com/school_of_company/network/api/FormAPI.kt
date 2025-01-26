package com.school_of_company.network.api

import com.school_of_company.model.model.form.FormRequestAndResponseModel
import com.school_of_company.network.dto.form.all.FormRequestAndResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FormAPI {

    @POST("/form/{expo_id}")
    suspend fun createForm(
        @Path("expo_id") expoId: String,
        @Body body: FormRequestAndResponse
    )

    @PATCH("/form/{form_id}")
    suspend fun modifyForm(
        @Path("form_id") formId: Long,
        @Body body: FormRequestAndResponse
    )

    @GET("/form/{form_id}")
    suspend fun getForm(
        @Path("form_id") formId: Long,
        @Query("formType") formType: String // TRAINEE, STANDARD
    ): FormRequestAndResponse

    @DELETE("/form/{form_id}")
    suspend fun deleteForm(
        @Path("form_id") formId: Long
    )
}