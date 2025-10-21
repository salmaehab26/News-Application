package com.example.newsapplication.data.Repository

import com.example.apiintegrationapp.response.Article
import com.example.newsapplication.data.remote.RetrofitInstance
import com.example.newsapplication.domain.INewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(
) : INewsRepository {

    override fun getNews(country: String): Flow<List<Article>> = flow {
        try {
            val response = RetrofitInstance.api.getNews(country, "72f42bc2588c4688a72cf0acc7ee280f")
            emit(response.articles)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }}