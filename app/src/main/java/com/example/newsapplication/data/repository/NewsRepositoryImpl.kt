package com.example.newsapplication.data.repository

import NewsRemoteMediator
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapplication.data.dataSource.local.INewsDao
import com.example.newsapplication.data.dataSource.local.NewsEntity
import com.example.newsapplication.data.dataSource.remote.IApiManager
import com.example.newsapplication.domain.repository.INewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl @Inject constructor(
    private val api: IApiManager,
    private val dao: INewsDao,
    @ApplicationContext private val context: Context
) : INewsRepository {

    override fun getPagedNews(): Flow<PagingData<NewsEntity>> {
        val pagingSourceFactory = { dao.getAllNewsPaging() }

        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = NewsRemoteMediator(api, dao, context),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
    override fun getLocalNewsCount(): Flow<Int> {
        return dao.getNewsCount()
    }
}
