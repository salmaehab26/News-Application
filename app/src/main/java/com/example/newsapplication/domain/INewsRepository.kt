package com.example.newsapplication.domain

import com.example.apiintegrationapp.response.Article
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    fun getNews(country: String): Flow<List<Article>>

}