package com.example.stackexchangetest.data

import com.example.stackexchangetest.data.model.StackExchangeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StackExchangeAPI {
    @GET("users")
    suspend fun searchUsers(
        @Query("inname") name: String?,
        @Query("page") page: Int,
        @Query("site") site: String = "stackoverflow",
        @Query("pagesize") pageSize: Int = 20,
        @Query("order") order: String = "asc",
        @Query("sort") sort: String = "name"
    ): Response<StackExchangeResponse>
}