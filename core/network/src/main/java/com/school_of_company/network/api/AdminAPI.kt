package com.school_of_company.network.api

import com.school_of_company.network.dto.admin.response.AdminRequestAllowListResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AdminAPI {

    @GET("/admin")
    suspend fun getAdminRequestAllowList() : List<AdminRequestAllowListResponse>

    @PATCH("/admin/{admin_id}")
    suspend fun allowAdmin(
        @Path("admin_id") adminId: Long
    )

    @DELETE("/admin")
    suspend fun serviceWithdrawal()
}