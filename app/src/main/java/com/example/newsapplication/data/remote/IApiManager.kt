package com.example.newsapplication.data.remote

import com.example.apiintegrationapp.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiManager {
    @GET("v2/top-headlines")
    suspend  fun getNews(@Query("country") country: String,
                         @Query("apiKey") apiKey: String,
                         @Query("page") page: Int ,
                         @Query("pageSize") pageSize: Int): NewsResponse
}