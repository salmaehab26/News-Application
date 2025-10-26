package com.example.newsapplication.domain.repository

import androidx.paging.PagingData
import com.example.newsapplication.data.dataSource.local.NewsEntity
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    fun getPagedNews(): Flow<PagingData<NewsEntity>>
    fun getLocalNewsCount(): Flow<Int>
}