package com.example.newsapplication.domain.repository

import com.example.apiintegrationapp.response.Article
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    fun getRemoteNews(): Flow<List<Article>>
    fun getLocalNews(): Flow<List<Article>>
}