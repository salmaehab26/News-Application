package com.example.newsapplication.domain.repository

import com.example.apiintegrationapp.response.Article
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    suspend fun getRemoteNews(page: Int, pageSize: Int): List<Article>
    fun getLocalNews(): Flow<List<Article>>
}