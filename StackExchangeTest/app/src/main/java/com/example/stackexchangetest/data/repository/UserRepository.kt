package com.example.stackexchangetest.data.repository

import com.example.stackexchangetest.data.StackExchangeAPI
import com.example.stackexchangetest.data.model.APIResponse
import com.example.stackexchangetest.data.model.StackExchangeResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository{
    private val BASE_URL = "https://api.stackexchange.com/2.3/"
    private var api: StackExchangeAPI

    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
                HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        api = retrofit.create(StackExchangeAPI::class.java)
    }
    suspend fun  searchUsers(query: String?, page: Int): APIResponse<StackExchangeResponse> {
        return try {
            val response = api.searchUsers(name = query, page = page)
            if (response.isSuccessful && response.body() != null){
                APIResponse.Success(response.body()!!)
            } else {
                APIResponse.Error(response.errorBody().toString())
            }
        }catch (e: Exception){
            APIResponse.Error(e.message ?: "Unknown error")
        }
    }

}